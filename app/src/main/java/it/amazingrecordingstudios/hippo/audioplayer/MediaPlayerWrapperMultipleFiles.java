package it.amazingrecordingstudios.hippo.audioplayer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

import it.amazingrecordingstudios.hippo.utils.Utils;

public class MediaPlayerWrapperMultipleFiles
        extends MediaPlayerWrapperOneFileAtATime {

    public static final String TAG = "MPWMultipleFiles";
    AssetFileDescriptor[] assetFileDescriptors;
    private boolean _lastFileHasPlayed;

    public MediaPlayerWrapperMultipleFiles() {
        super();

        this.setListeners(onPreparedListener,
                onCompletionListener,
                onErrorListener);
    }

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

    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer player) {

//            player.isPlaying();
//            player.setNextMediaPlayer();
//            player.setOnCompletionListener();
//
//            player.setOnErrorListener();
//            player.selectTrack

            setCurrentPlayerState(PlayerState.PREPARED);
            Log.v(TAG,"Player completed preparing. Starting to play..");
            player.start();
            // redundant: _mediaPlayer.setCurrentPlayerState(PlayerState.PLAYING);
            // handle events during playback?

            processQueuedCallsWhilePreparing();
        }
    };

    OnCompletionListener onCompletionListener = new OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            setCurrentPlayerState(PlayerState.COMPLETED);
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
                reset(KEEP_AUDIO_FILES);
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
