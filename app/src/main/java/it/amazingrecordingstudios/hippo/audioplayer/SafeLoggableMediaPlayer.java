package it.amazingrecordingstudios.hippo.audioplayer;

import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class SafeLoggableMediaPlayer extends LoggableMediaPlayer {

    public static final String TAG = "SLMediaPlayer";

    public final static List<PlayerState> validStatesTowardsStop;
    public final static List<PlayerState> validStatesTowardsPrepareAsynch;
    public final static Map<PlayerState,
            List<PlayerState>> validTransitions;
    static {
        {   PlayerState[] validStatesTowardsStopTmp
                = {PlayerState.PREPARED,
                PlayerState.PREPARING,
                PlayerState.PLAYING,
                PlayerState.STOPPED,
                PlayerState.PAUSED,
                PlayerState.COMPLETED};
            validStatesTowardsStop = Arrays.asList(validStatesTowardsStopTmp);
        }

        {
            PlayerState[] validStatesTowardsPrepareAsynchTmp
                    = {PlayerState.INITIALIZED,
                    PlayerState.STOPPED};
            validStatesTowardsPrepareAsynch = Arrays.asList(validStatesTowardsPrepareAsynchTmp);
        }

        validTransitions = new TreeMap<>();
        validTransitions.put(PlayerState.STOPPED, validStatesTowardsStop);
        validTransitions.put(PlayerState.PREPARING, validStatesTowardsPrepareAsynch);
    }

    protected boolean receivedCallsWhilePreparing;

    protected ArrayList<PlayerState> callsWhilePreparing;

    public SafeLoggableMediaPlayer() {
        super();

        this.receivedCallsWhilePreparing = false;
        this.callsWhilePreparing = new ArrayList<>();
    }

    abstract public void play() ;

    protected void enqueueCall(PlayerState nextCall) {
        if(this.isPreparing()) {
            this.callsWhilePreparing.add(nextCall);
            this.receivedCallsWhilePreparing = true;
        } else {
            Log.e(TAG,"Trying to enqueue call while not in preparing state");
        }
    }

    protected void processQueuedCallsWhilePreparing() {
        for(PlayerState stateCall:this.callsWhilePreparing) {
            if(stateCall == PlayerState.STOPPED) {
                this.stop();
            } else if(stateCall == PlayerState.PAUSED) {
                this.pause();
            } else if(stateCall == PlayerState.PLAYING) {
                this.play();
            } else {
                Log.d(TAG,"Ignoring call queued while preparing: "
                        + stateCall);
                //TODO handle more states
            }
        }

        clearQueuedCallsWhilePreparing();
    }

    protected void clearQueuedCallsWhilePreparing() {
        this.callsWhilePreparing.clear();
        receivedCallsWhilePreparing = false;
    }

    public boolean isStateValidForPrepareAsynch() {

        return validStatesTowardsPrepareAsynch.contains(getCurrentPlayerState());
    }

    public boolean isStateInValidForStop() {
        return !isValidStateForStop(currentPlayerState);
                /*
    *     public final static PlayerState[] invalidStatesForStop
        = {PlayerState.IDLE, PlayerState.INITIALIZED, PlayerState.ERROR,
        PlayerState.UNKNOWN, PlayerState.END_RELEASED_UNAVAILABLE};

    * */
    }

    public boolean isValidStateForStop(PlayerState state) {

        return validStatesTowardsStop.contains(state);
    }

    protected void tryPrepareAsynch() {

        if(this.isStateValidForPrepareAsynch()) {
            try {
                this.prepareAsync();
            } catch (IllegalStateException e) {
                Log.e(TAG,e.toString());
                processQueuedCallsWhilePreparing();
            }
        }
    }

    public void pauseOrResume() {
        if(this.isPlaying()) {
            this.pause();
        } else if(this.isPaused()) {
            this.resume();
        } else {
            Log.e(TAG,"Trying to pauseOrResume in an unaccepted state: "
                    + this.getCurrentPlayerState());
        }
    }

    @Override
    public void pause() throws IllegalStateException {

        if (this.isPaused()
                || this.hasCompletedPlaying()) {
            return;
        }

        if(this.isPreparing()) {
            Log.d(TAG,"Queuing pause() call received while in PREPARING state, to avoid error");
            this.enqueueCall(PlayerState.PAUSED);
            return;
        }

        if(this.isPlaying()) {
            super.pause();
            return;
        }

        if(this.isStopped()) {
            Log.d(TAG,"Trying to pause while stopped, staying stopped");
        }

        Log.e(TAG,"Trying to pause in an unaccepted state: "
                + this.getCurrentPlayerState());

    }

    public void resume() {
        if(this.isPaused()) {
            this.start();
        } else  {
            Log.e(TAG,"Trying to resume when not paused: "
                    + this.getCurrentPlayerState());
        }
    }

    OnErrorListener onErrorListener = new OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            Log.e(TAG,"what: " + what
                    + " extra: " + extra + " " + mp.toString());

            return false;
        }
    };
}
