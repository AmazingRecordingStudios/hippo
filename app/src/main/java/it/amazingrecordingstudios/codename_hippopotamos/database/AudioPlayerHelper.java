package it.amazingrecordingstudios.codename_hippopotamos.database;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.amazingrecordingstudios.codename_hippopotamos.utils.Utils;

public class AudioPlayerHelper implements Closeable {

    public enum PlayerState { /*UNKNOWN,*/ IDLE, INITIALIZED,
        PREPARING, PREPARED, PLAYING, PAUSED, COMPLETED,
        STOPPED, ERROR, END_RELEASED_UNAVAILABLE
    }

    public final static List<PlayerState> validStatesTowardsStop;
    public final static List<PlayerState> validStatesTowardsPrepareAsynch;
    public final static Map<PlayerState, List<PlayerState>> validTransitions;
    static {
        {   PlayerState[] validStatesTowardsStopTmp
                    = {PlayerState.PREPARED, PlayerState.PREPARING, PlayerState.PLAYING,
                    PlayerState.STOPPED, PlayerState.PAUSED, PlayerState.COMPLETED};
            validStatesTowardsStop = Arrays.asList(validStatesTowardsStopTmp);
        }

        {
            PlayerState[] validStatesTowardsPrepareAsynchTmp
                    = {PlayerState.INITIALIZED,PlayerState.STOPPED};
            validStatesTowardsPrepareAsynch = Arrays.asList(validStatesTowardsPrepareAsynchTmp);
        }

        validTransitions = new TreeMap<>();
        validTransitions.put(PlayerState.STOPPED, validStatesTowardsStop);
        validTransitions.put(PlayerState.PREPARING, validStatesTowardsPrepareAsynch);
    }

    public abstract class LoggableMediaPlayer extends MediaPlayer {

        private static final String TAG = "SafeLoggableMediaPlayer" ;

        protected PlayerState currentPlayerState;

        public LoggableMediaPlayer() {
            super();

            //setCurrentPlayerState(PlayerState.UNKNOWN);
            Log.d(TAG,"New LoggableMediaPlayer");
            setCurrentPlayerState(PlayerState.IDLE);
        }

        public PlayerState getCurrentPlayerState() {
            return currentPlayerState;
        }

        public boolean isPaused() {
            return  currentPlayerState == PlayerState.PAUSED;
        }

        public boolean hasCompletedPlaying() {
            return currentPlayerState == PlayerState.COMPLETED;
        }

        public boolean isPreparing() {
            return currentPlayerState == PlayerState.PREPARING;
        }

        public boolean isIdle() {
            return currentPlayerState == PlayerState.IDLE;
        }

        public boolean isStopped() {
            return currentPlayerState == PlayerState.STOPPED;
        }

        public boolean isInitialized() {
            return currentPlayerState == PlayerState.INITIALIZED;
        }

        protected void setCurrentPlayerState(PlayerState state) {
            Log.v(TAG,"Going from " + getCurrentPlayerState()
                    + " to " + state);
            this.currentPlayerState = state;
        }

        public void setListeners(OnPreparedListener onPreparedListener,
                                 OnCompletionListener onCompletionListener,
                                 OnErrorListener onErrorListener) {

            this.setOnPreparedListener(onPreparedListener);
            this.setOnCompletionListener(onCompletionListener);
            this.setOnErrorListener(onErrorListener);
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        public void setDefaultAudioAttributes() {
            if(android.os.Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.LOLLIPOP) {
                final android.media.AudioAttributes DEFAULT_AUDIO_ATTRIBUTES
                        = new android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_VOICE_COMMUNICATION)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build();

                this.setAudioAttributes(DEFAULT_AUDIO_ATTRIBUTES);
            }
        }

        @Override
        public void prepareAsync() throws IllegalStateException {
            try {
                super.prepareAsync();
                setCurrentPlayerState(PlayerState.PREPARING);
            } catch (Exception e) {
                Log.e(TAG,e.toString());
            }
        }

        @Override
        public void start() throws IllegalStateException {
            super.start();
            setCurrentPlayerState(PlayerState.PLAYING);
        }

        @Override
        public void stop() throws IllegalStateException {
            super.stop();
            setCurrentPlayerState(PlayerState.STOPPED);
        }

        @Override
        public void release() {
            super.release();
            setCurrentPlayerState(PlayerState.END_RELEASED_UNAVAILABLE);
        }

        @Override
        public void reset() {
            Log.d(TAG,"resetting player");
            super.reset();
            setCurrentPlayerState(PlayerState.IDLE);
        }

        @Override
        public void pause() throws IllegalStateException {
            super.pause();
            setCurrentPlayerState(PlayerState.PAUSED);
        }

        @Override
        public void setDataSource(FileDescriptor fd, long offset, long length) throws IOException, IllegalArgumentException, IllegalStateException {
            super.setDataSource(fd, offset, length);

            //this method is for compatibility with SDK API < 24
            setCurrentPlayerState(PlayerState.INITIALIZED);
        }

        @Override
        public void prepare() throws IOException, IllegalStateException {
            super.prepare();
            setCurrentPlayerState(PlayerState.PREPARED);
        }

        @Override
        public void seekTo(int msec) throws IllegalStateException {
            super.seekTo(msec);
        }

        @Override
        public void setDataSource(String path) throws IOException,
                IllegalArgumentException, IllegalStateException, SecurityException {
            super.setDataSource(path);

            //this method works only on SDK API >= 24
            setCurrentPlayerState(PlayerState.INITIALIZED);
        }
    }

    public abstract class SafeLoggableMediaPlayer extends LoggableMediaPlayer {

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

        MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                Log.e(TAG,"what: " + what
                        + " extra: " + extra + " " + mp.toString());

                return false;
            }
        };
    }

    public abstract class MediaPlayerWrapperOneFileAtATime extends SafeLoggableMediaPlayer {

        @RequiresApi(Build.VERSION_CODES.N)
        protected void setDataSource(MediaPlayer mp,
                                   AssetFileDescriptor assetFileDescriptor)
                throws  IOException{
            mp.setDataSource(assetFileDescriptor);
        }

        @RequiresApi(Build.VERSION_CODES.BASE)
        protected void compatibleSetDataSource(
                MediaPlayer mp,
                AssetFileDescriptor assetFileDescriptor)
                throws  IOException{
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            long offset = assetFileDescriptor.getStartOffset();
            long length = assetFileDescriptor.getLength();
            mp.setDataSource(
                    fileDescriptor,
                    offset,
                    length);
        }
    }

    public class MediaPlayerWrapperMultipleFiles
            extends MediaPlayerWrapperOneFileAtATime {

        AssetFileDescriptor[] assetFileDescriptors;
        private boolean _lastFileHasPlayed;

        public int filesCount() {
            if(Utils.isNullOrEmpty(assetFileDescriptors)) {
                return 0;
            }

            return assetFileDescriptors.length;
        }

        private int getCurrentTrackIdx() {
            return _currentTrackIdx;
        }

        private int getLastTrackIdx() {
            return filesCount() - 1;
        }

        public void initCurrentTrackIdx() {
            this._currentTrackIdx = 0;
        }

        private void incrementCurrentTrackIdx() {
            this._currentTrackIdx++;

            if(this._currentTrackIdx >= filesCount()) {
                initCurrentTrackIdx();
            }
        }

        private int _currentTrackIdx;

        public boolean firstFilePlayedAtLeastOnce;

        public boolean hasLastFilePlayed() {
            return _lastFileHasPlayed;
        }

        public void setLastFileHasPlayed(boolean hasPlayed) {
            Log.d(TAG,"setting _lastFileHasPlayed to " + hasPlayed
                    + " was " + this._lastFileHasPlayed);
            Log.d(TAG,"there are " + this.filesCount()
                    + " audioFiles, last idx is " + this.getLastTrackIdx());

            this._lastFileHasPlayed = hasPlayed;
        }

        protected void tryInsertFileIntoMediaplayer(
                AssetFileDescriptor assetFileDescriptor) {

            // this call to make sure we're not calling setDataSource in an invalid state
            // call to mp.reset() should be valid in any state
            if(!this.isIdle()) {
                final boolean KEEP_AUDIO_FILES = true;
                this.reset(KEEP_AUDIO_FILES);
            }

            //This might not be necessary
            if(!this.isIdle()) {
                Log.e(TAG,"Previous call to reset was not successful, currently in state"
                        + getCurrentPlayerState() + " , can't call setDataSource");
                return;
            }

            if(assetFileDescriptor == null) {
                Log.d(TAG,"Null File Descriptor, skipping setDataSource, staying in IDLE");
                return;
            }

            try {
                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setDataSource(this,assetFileDescriptor);
                }
                else {
                    compatibleSetDataSource(this,assetFileDescriptor);
                }
            } catch (IllegalArgumentException e) {
                Log.e(TAG,e.toString());
            } catch (IOException e) {
                Log.e(TAG,e.toString());
            } catch (IllegalStateException e) {
                //TODO should we change to error state?
                //_mediaPlayer.setCurrentPlayerState(PlayerState.ERROR)
                Log.e(TAG,e.toString());
            }
        }

        @Override
        public void reset() {
            if(!this.isIdle()) {
                super.reset();
            }

            this.assetFileDescriptors = null;
        }

        public void reset(boolean keepAudioFiles) {
            AssetFileDescriptor[] tmpAssetFileDescriptors
                    = this.assetFileDescriptors;

            this.reset();

            if(keepAudioFiles) {
                this.assetFileDescriptors = tmpAssetFileDescriptors;
            }
        }

        //TODO test when passing null argument, was raising exception
        //TODO test because creation of asset file descriptor has been
        // postponed to end of method
        public void changeAudioFiles(AssetFileDescriptor[] newAudioAssetFileDescriptors)
                throws IOException {

            CloseAssetFileDescriptors();

            initCurrentTrackIdx();
            this.firstFilePlayedAtLeastOnce = false;
            this.setLastFileHasPlayed(false);

            this.assetFileDescriptors = newAudioAssetFileDescriptors;

            if(!Utils.isNullOrEmpty(this.assetFileDescriptors)) {

                final int FIRST_ELEMENT_INDEX = 0;

                //NB this calls reset() and setDataSource(), then state should be INITIALIZED
                tryInsertFileIntoMediaplayer(this.assetFileDescriptors[FIRST_ELEMENT_INDEX]);
            }

            //TODO FIXME ADDTEST
            // after changing files, should be called reset()
            //Test: check state after changing files

            //_mediaPlayer.reset(); and set data source with the new files
        }

        private void playNext(int trackIdx) {

            if(Utils.isNullOrEmpty(assetFileDescriptors)
                    || this.filesCount() <= trackIdx) {
                //TODO FIXME this sometimes happen, should handle properly and add tests
                // possibly because inconsistent state after changing audio files
                // (fewer in number)
                Log.e(TAG,"Play next: track index " + trackIdx
                        + " out of bounds " + assetFileDescriptors);
                return;
            }

            //setDataSource, if ok state goes from idle to initialized
            //TODO FIXME this method call is misleading
            tryInsertFileIntoMediaplayer(assetFileDescriptors[trackIdx]);

            tryPrepareAsynch();
        }

        @Override
        public void play() {

            if(Utils.isNullOrEmpty(this.assetFileDescriptors)) {
                Log.e(TAG,"No files to play " + this.assetFileDescriptors);
                return;
            }

            if(!this.firstFilePlayedAtLeastOnce
                    && this.isIdle()) {
                // first playback
                this.firstFilePlayedAtLeastOnce = true;
                initCurrentTrackIdx();
                Log.d(TAG,"Play request accepted, first play or idle");
                playNext(getCurrentTrackIdx());
            } else if(isPaused()) {
                this.start();
            } else if(this.isStopped()) {
                //removed: && hasLastFilePlayed() which was causing a bug
                //TODO FIXME distinguish between playing the initial series of files
                // or calling changeAudioFiles/reset because we need to change files
                // but changeAudioFiles/reset is usually already called by setting the new files

                // we start the loop of audio tracks again
                initCurrentTrackIdx();
                Log.d(TAG,"Play request accepted from stopped");
                playNext(getCurrentTrackIdx());
            } else if(this.hasCompletedPlaying()) {

                //we should start back from the first file
                Log.d(TAG,"Play request after completed state, replaying from first track");
                this.initCurrentTrackIdx();
                playNext(getCurrentTrackIdx());

            } else if(isPlaying()) {
                //ignoring, keep playing
            }
            else if(this.isInitialized()) {
                tryPrepareAsynch();
            } else if(this.isPreparing()) {
                this.enqueueCall(PlayerState.PLAYING);
            }
            else {
                String msg = "Play request non accepted state, ignoring. State: "
                        + this.getCurrentPlayerState();
                Log.v(TAG,msg);
            }

            // gets a series of audio files (asset file descriptors)
            // sets the first one into media player with setDataSource
            // starts prepareAsync

            //in the handler onPrepared, play the sound player.start();

            // onCompletion , (stop) set the next file on the list (unless is the last one),
            // and call prepare asynch
            // if it's the last one set some variable, lastFilePlayed = true
            // in the handler onPrepared, if(lastFilePlayed) do nothing
            // (add a method 'playAgain', callable externally)
            // if(!lastFilePlayed) call 'playAgain'
            // in 'playAgain', to avoid errors, check if isPlaying (than to nothing) and other states

            // keep in a variable the supposed current state of the media player, to avoid errors

            //add externally callable method close (closeable), from which we release the media player
        }

        @Override
        public void stop() {

            //accepted states {Prepared, Started, Stopped, Paused, PlaybackCompleted}
            //non accepted states {Idle, Initialized, Error}

            if(this.isStateInValidForStop()) {
                String msg = "Stop request from non accepted state, ignoring. State: "
                        + this.getCurrentPlayerState();
                Log.e(TAG,msg);

            } else if(this.isPreparing()) {
                Log.d(TAG,"Queuing stop() call received while in PREPARING state, to avoid error");
                this.enqueueCall(PlayerState.STOPPED);
            } else {
                super.stop();
                //setLastFileHasPlayed(false);
                initCurrentTrackIdx();
            }
        }

        private void CloseAssetFileDescriptors() throws IOException {

            if(Utils.isNullOrEmpty(this.assetFileDescriptors)) {
                return;
            }

            for (AssetFileDescriptor assetFileDescriptor:this.assetFileDescriptors) {
                try {
                    if(assetFileDescriptor != null) {
                        assetFileDescriptor.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG,e.toString());
                }
            }
        }

        public void close() throws IOException {

            this.release();
            CloseAssetFileDescriptors();
        }

        MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                _mediaPlayer.setCurrentPlayerState(PlayerState.COMPLETED);
                //FIXME this stop() might be causing the error:
                //E/MediaPlayer: stop called in state 0
                //E/MediaPlayer: error (-38, 0)
                //mp.stop();
                //_mediaPlayer.setCurrentPlayerState(PlayerState.STOPPED);
                //FIXME: after removing stop, causes:
                //D/AudioPlayerHelper: Play request non accepted state, ignoring. State: COMPLETED

                //NB from completed, to play again whole loop,
                // call start()

                //FIXME: not playing file after first play
                // seems to be assigned incorrectly)
                setLastFileHasPlayed(getCurrentTrackIdx() == getLastTrackIdx());
                if(!hasLastFilePlayed()) {
                    Log.d(TAG,"first track has played but there are more");
                    //play next
                    final boolean KEEP_AUDIO_FILES = true;
                    _mediaPlayer.reset(KEEP_AUDIO_FILES);
                    //redundant: _mediaPlayer.setCurrentPlayerState(PlayerState.IDLE);
                    incrementCurrentTrackIdx();
                    playNext(getCurrentTrackIdx());
                }
                else {
                    // we stay in stopped state. if activity changes screens..
                    // setLastFileHasPlayed(true);
                    // TODO ..
                    // ..
                    // only now we can accept another call to play()
                    // otherwise we ignore them
                    Log.d(TAG,"last track has played.");
                }
                // we should check if we should play next track
                // or if we have already played the last one

                //TODO
                // release would be only when quitting activity
                // not when changing to other screen with other audio quotes
            }
        };
    }

    public static final String TAG = "AudioPlayerHelper";

    public boolean isPlaying() {
        return this._mediaPlayer.isPlaying();
    }

    public boolean isPaused() {
        return this._mediaPlayer.isPaused();
    }

    public boolean isPlayingOrPaused() {
        return isPlaying() || isPaused();
    }

    MediaPlayerWrapperMultipleFiles _mediaPlayer;

    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer player) {

//            player.isPlaying();
//            player.setNextMediaPlayer();
//            player.setOnCompletionListener();
//
//            player.setOnErrorListener();
//            player.selectTrack

            _mediaPlayer.setCurrentPlayerState(PlayerState.PREPARED);
            Log.v(TAG,"Player completed preparing. Starting to play..");
            player.start();
            // redundant: _mediaPlayer.setCurrentPlayerState(PlayerState.PLAYING);
            // handle events during playback?

            _mediaPlayer.processQueuedCallsWhilePreparing();
        }
    };

    public AudioPlayerHelper() throws IOException {
        this((AssetFileDescriptor) null);
    }

    public AudioPlayerHelper(ArrayList<String> audioFilePaths,
                             AssetManager assetManager) throws IOException {

        this(Utils.getAssetFileDescriptors(Utils.toArray(audioFilePaths), assetManager));
    }

    public AudioPlayerHelper(AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(new AssetFileDescriptor[]{assetFileDescriptor});
    }

    public AudioPlayerHelper(AssetFileDescriptor[] assetFileDescriptors) throws IOException {

        setUpMediaPlayer();

        if(!Utils.isNullOrEmpty(assetFileDescriptors)) {

            this._mediaPlayer.changeAudioFiles(assetFileDescriptors);
        }
    }

    @Keep
    public void changeAudioFiles(AssetFileDescriptor newAssetFileDescriptor)
            throws IOException {
        AssetFileDescriptor[] assetFileDescriptors
                = {newAssetFileDescriptor};

        this.changeAudioFiles(assetFileDescriptors);
    }

    public void changeAudioFiles(AssetFileDescriptor[] newAssetFileDescriptors)
            throws IOException {

        this._mediaPlayer.changeAudioFiles(newAssetFileDescriptors);
    }

    private void setUpMediaPlayer() {

        this._mediaPlayer = new MediaPlayerWrapperMultipleFiles();
        //redundant: _mediaPlayer.setCurrentPlayerState(PlayerState.IDLE);

        this._mediaPlayer.setListeners(onPreparedListener,
                _mediaPlayer.onCompletionListener,_mediaPlayer.onErrorListener);

        if(android.os.Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.LOLLIPOP) {
            setMPDefaultAudioAttributes(this._mediaPlayer);
        } else {
            //mediaPlayer.setAudioStreamType(..);
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setMPDefaultAudioAttributes(
            SafeLoggableMediaPlayer mp) {
        if(android.os.Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.LOLLIPOP) {
            mp.setDefaultAudioAttributes();
        }
    }

    @Override
    public void close() throws IOException {
        this._mediaPlayer.close();
    }

    public static void playQuotes(String[] quotesAssetsPaths,
                                  AudioPlayerHelper player,
                                  AssetManager assetManager) {

        String[] validAssetPaths
                = DBUtils.filterNonNullElements(quotesAssetsPaths);

        //TODO check that audioplayer is not null

        //TODO show toast for non existing audio files
        // possibility: keep arraylists: one for existing audio files
        // one for quotes that have none
        // one for quotes that should have one but is missing

        //TODO more tests for valid state transitions

        AssetFileDescriptor[] assetFileDescriptors
                = Utils.getAssetFileDescriptors(validAssetPaths,
                assetManager);
        try {
            player._mediaPlayer.changeAudioFiles(assetFileDescriptors);
            player._mediaPlayer.play();
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }
    }

    public void resetAndRemoveFilesFromPlayer() {
        this._mediaPlayer.reset();
    }

    public void stop() {
        this._mediaPlayer.stop();
    }

    public void pauseOrResume() {
        this._mediaPlayer.pauseOrResume();
    }

    public void pause() {
        this._mediaPlayer.pause();
    }

    @Keep
    public void play() {
        this._mediaPlayer.play();
    }

    @Keep
    public int filesCount() {
        return this._mediaPlayer.filesCount();
    }
}
