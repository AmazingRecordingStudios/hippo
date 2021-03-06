package it.amazingrecordingstudios.hippo.audioplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

import it.amazingrecordingstudios.hippo.database.DBUtils;
import it.amazingrecordingstudios.hippo.utils.Utils;

public class AudioPlayerHelper implements Closeable {

    //TODO implement as Singleton class

    public static final String TAG = "AudioPlayerHelper";

    private static MediaPlayerWrapperMultipleFiles _mediaPlayer;

    static {
        setUpMediaPlayer();
    }

    private static void setUpMediaPlayer() {

        _mediaPlayer = new MediaPlayerWrapperMultipleFiles();
        //redundant: _mediaPlayer.setCurrentPlayerState(PlayerState.IDLE);

        if(android.os.Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.LOLLIPOP) {
            setMPDefaultAudioAttributes(_mediaPlayer);
        } else {
            //mediaPlayer.setAudioStreamType(..);
        }
    }

    public boolean isPlaying() {
        return _mediaPlayer.isPlaying();
    }

    public boolean isPaused() {
        return _mediaPlayer.isPaused();
    }

    public boolean isPlayingOrPaused() {
        return isPlaying() || isPaused();
    }

    public AudioPlayerHelper() {
        if(_mediaPlayer.isReleased()) {
            setUpMediaPlayer();
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

    public void changeAudioFiles(ArrayList<String> audioFilePaths,
                             AssetManager assetManager) throws IOException {

        this.changeAudioFiles(Utils.getAssetFileDescriptors(Utils.toArray(audioFilePaths), assetManager));
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

        _mediaPlayer.changeAudioFiles(newAssetFileDescriptors);
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
        _mediaPlayer.close();
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
        _mediaPlayer.reset();
    }

    public void stop() {
        _mediaPlayer.stop();
    }

    public void pauseOrResume() {
        _mediaPlayer.pauseOrResume();
    }

    public void pause() {
        _mediaPlayer.pause();
    }

    @Keep
    public void play() {
        _mediaPlayer.play();
    }

    @Keep
    public int filesCount() {
        return _mediaPlayer.filesCount();
    }
}
