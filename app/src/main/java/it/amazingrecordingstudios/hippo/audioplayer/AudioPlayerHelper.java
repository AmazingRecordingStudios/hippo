package it.amazingrecordingstudios.hippo.audioplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

import it.amazingrecordingstudios.hippo.database.DBUtils;
import it.amazingrecordingstudios.hippo.utils.Utils;
import it.amazingrecordingstudios.hippo.audioplayer.LoggableMediaPlayer.PlayerState;

public class AudioPlayerHelper implements Closeable {

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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static android.media.AudioAttributes getDefaultAudioAttributes() {
        final AudioAttributes DEFAULT_AUDIO_ATTRIBUTES
                = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        return DEFAULT_AUDIO_ATTRIBUTES;
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

        this._mediaPlayer = new MediaPlayerWrapperMultipleFiles(this);
        //redundant: _mediaPlayer.setCurrentPlayerState(PlayerState.IDLE);

        this._mediaPlayer.setListeners(_mediaPlayer.onPreparedListener,
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
