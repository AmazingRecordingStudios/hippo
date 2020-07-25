package it.amazingrecordingstudios.hippo.audioplayer;

import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.FileDescriptor;
import java.io.IOException;

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
        if(Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.LOLLIPOP) {

           this.setAudioAttributes(AudioPlayerHelper.getDefaultAudioAttributes());
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

    public enum PlayerState { /*UNKNOWN,*/ IDLE, INITIALIZED,
        PREPARING, PREPARED, PLAYING, PAUSED, COMPLETED,
        STOPPED, ERROR, END_RELEASED_UNAVAILABLE
    }
}
