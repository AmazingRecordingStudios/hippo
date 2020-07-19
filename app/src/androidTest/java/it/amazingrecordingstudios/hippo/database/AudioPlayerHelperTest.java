package it.amazingrecordingstudios.hippo.database;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import it.amazingrecordingstudios.hippo.Globals;
import it.amazingrecordingstudios.hippo.SharedTestUtils;
import it.amazingrecordingstudios.hippo.database.AudioPlayerHelper.PlayerState;
import it.amazingrecordingstudios.hippo.utils.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AudioPlayerHelperTest {

    AssetManager assetManager;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assetManager = appContext.getAssets();
    }

    @After@Suppress
    public void tearDown() throws Exception {
    }

    @Test
    public void stopWhilePreparing() throws IOException{
        AudioPlayerHelper player = getPreparingMP();
        assertEquals(PlayerState.PREPARING,player._mediaPlayer.getCurrentPlayerState());

        player.stop();
        waitForStateWhilePreparing(player, PlayerState.STOPPED);
    }

    @Test
    public void pauseWhilePreparing() throws IOException{

        AudioPlayerHelper player = getPreparingMP();
        assertEquals(PlayerState.PREPARING,player._mediaPlayer.getCurrentPlayerState());

        player.pause();
        waitForStateWhilePreparing(player, PlayerState.PAUSED);
    }

    private void waitForFirstTrackFinishPreparingAndPlaying(AudioPlayerHelper player) {

        final String TAG = "waitForFirstTrackComplete";

        if(!isPreparingOrPlaying(player)) {
            fail("(first) track is not preparing or playing: "
                    + player._mediaPlayer.getCurrentPlayerState());
        }

        int maxAttempts = 20;
        int waitDurationMillis = 100;
        for(int attempts=0; attempts<maxAttempts; attempts++) {
            if(isPreparingOrPlaying(player)) {
                try {
                    Thread.sleep(waitDurationMillis);
                } catch (InterruptedException e) {
                    Log.e(TAG,e.toString());
                }
            } else {
                // finished playing
                break;
            }
        }
    }

    private void waitForTrackSwitch(AudioPlayerHelper player) {
        final String TAG = "waitForTrackSwitch";

        if(!(player.isPlaying() || player._mediaPlayer.hasCompletedPlaying())) {
            fail("Player is not playing or completed: "
                    + player._mediaPlayer.getCurrentPlayerState());
        }

        int maxAttempts = 20;
        int waitDurationMillis = 100;
        for(int attempts=0; attempts<maxAttempts; attempts++) {
            if(player.isPlaying()) {
                try {
                    Thread.sleep(waitDurationMillis);
                } catch (InterruptedException e) {
                    Log.e(TAG,e.toString());
                }
            } else {
                if(player._mediaPlayer.hasCompletedPlaying()
                        || player._mediaPlayer.isIdle()
                        || player._mediaPlayer.isInitialized()) {
                    Log.d(TAG,"Player has started switching tracks.");
                }
                else {
                    // FIXME some tests sometimes fail here, unexpected state PLAYING
                    // test fails is non deterministic and happens in different tests
                    fail("Unexpected player state: "
                            + player._mediaPlayer.getCurrentPlayerState());
                }
                break;
            }
        }
    }

    private boolean isInTrackSwitchingStates(AudioPlayerHelper player) {
        return player._mediaPlayer.hasCompletedPlaying()
                || player._mediaPlayer.isIdle()
                || player._mediaPlayer.isInitialized();
    }

    private boolean isPreparingOrPlaying(AudioPlayerHelper player) {

        return player._mediaPlayer.isPreparing()
                || player._mediaPlayer.isPlaying();
    }

    private void waitForNextTrackPreparing(AudioPlayerHelper player) {

        final String TAG = "waitForNextTrackPreparing";

        if(!isInTrackSwitchingStates(player)) {
            if(isPreparingOrPlaying(player)) {
               return;
            }
            else {
                fail("Player is not switching track, nor playing the next one");
            }
        }

        int maxAttempts = 20;
        int waitDurationMillis = 100;
        for(int attempts=0; attempts<maxAttempts; attempts++) {
            if(isInTrackSwitchingStates(player)) {
                try {
                    Thread.sleep(waitDurationMillis);
                } catch (InterruptedException e) {
                    Log.e(TAG,e.toString());
                }
            } else {
                if(isPreparingOrPlaying(player)) {
                    Log.d(TAG,"Player has started preparing or playing next track");
                }
                else {
                    fail("Unexpected player state: "
                            + player._mediaPlayer.getCurrentPlayerState());
                }
                break;
            }
        }
    }

    private void waitForStateWhilePreparing(AudioPlayerHelper player,
                                            PlayerState expectedState) {

        final String TAG = "waitFor " + expectedState + " StateWhilePreparing";

        int maxAttempts = 20;
        int waitDurationMillis = 100;
        for(int attempts=0; attempts<maxAttempts; attempts++) {
            if(player._mediaPlayer.isPreparing()) {
                try {
                    Thread.sleep(waitDurationMillis);
                } catch (InterruptedException e) {
                    Log.e(TAG,e.toString());
                }
            } else {
                Log.d(TAG,"Player prepared after wait attempts: " + attempts);
                break;
            }
        }

        if(player._mediaPlayer.isPreparing()) {
            Log.e(TAG,"Unable to wait for player to complete");
            fail("Unable to wait for player to complete");
        } else {
            assertEquals(expectedState,player._mediaPlayer.getCurrentPlayerState());
        }
    }

    @Test
    public void stop() throws IOException{
        //TODO
        // get a media player
        // set it to all valid states for stop, stop it at each one,
        // check that no errors occur, and state is stopped
        // set it to all invalid states for stop, check that error/warning is reported
        // but no crash, and the resulting state is appropriate

        AudioPlayerHelper player = getIdleMP();
        assertEquals(PlayerState.IDLE,player._mediaPlayer.getCurrentPlayerState());

        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }

        assertEquals(PlayerState.IDLE,player._mediaPlayer.getCurrentPlayerState());

        player = getInitializedMP();
        assertEquals(PlayerState.INITIALIZED,player._mediaPlayer.getCurrentPlayerState());

        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.INITIALIZED,player._mediaPlayer.getCurrentPlayerState());

        //NOTE: call to player.play() would result in prepareAcynh
        // which switches to the PREPARING transient state, any
        // call during this state is undefined

        player = getPreparedMP();
        assertEquals(PlayerState.PREPARED,player._mediaPlayer.getCurrentPlayerState());
        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.STOPPED,player._mediaPlayer.getCurrentPlayerState());

        player = getStartedMP();
        assertEquals(PlayerState.PLAYING,player._mediaPlayer.getCurrentPlayerState());
        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.STOPPED,player._mediaPlayer.getCurrentPlayerState());

        player = getPausedMP();
        assertEquals(PlayerState.PAUSED,player._mediaPlayer.getCurrentPlayerState());
        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.STOPPED,player._mediaPlayer.getCurrentPlayerState());

        //TODO test asynch for playback complete (or very short track and loop)
    }

    @Test
    public void stopFromInitialized() throws IOException{
        AudioPlayerHelper player = getInitializedMP();
        assertEquals(PlayerState.INITIALIZED,player._mediaPlayer.getCurrentPlayerState());
        try {
            player.stop();
        } catch (Exception e) {
            fail("Media player exception when stopping from idle: " + e.toString());
        }
        assertEquals(PlayerState.INITIALIZED,player._mediaPlayer.getCurrentPlayerState());
    }

    private AudioPlayerHelper getIdleMP() throws IOException {

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper();
        return audioPlayerHelper;
    }

    private AudioPlayerHelper getInitializedMP() throws IOException {

        AudioPlayerHelper audioPlayerHelper = getIdleMP();
        audioPlayerHelper.changeAudioFiles(getSingleAudioFileDescriptor(assetManager));

        return audioPlayerHelper;
    }

    private AudioPlayerHelper getPreparingMP() throws IOException {
        AudioPlayerHelper player = getInitializedMP();
        player._mediaPlayer.tryPrepareAsynch();

        return player;
    }


    private AudioPlayerHelper getPreparedMP() throws IOException {
        AudioPlayerHelper player = getInitializedMP();
        player._mediaPlayer.prepare();

        return player;
    }

    private AudioPlayerHelper getStartedMP() throws IOException {
        AudioPlayerHelper player = getPreparedMP();
        player._mediaPlayer.start();

        return player;
    }

    private AudioPlayerHelper getStoppedMP() throws IOException {

        AudioPlayerHelper player = getStartedMP();
        player.stop();

        return player;
    }

    private AudioPlayerHelper getPausedMP() throws IOException {
        AudioPlayerHelper player = getStartedMP();
        player.pause();

        return player;
    }

    private AudioPlayerHelper getCompletedMP() {

        //TODO this might be needed for other tests

        //should mock state of underling MediaPlayer
        throw new UnsupportedOperationException();
    }

    @Test@Ignore
    public void playerIllegalStates() {
        //TODO more tests for potential player illegal or incorrect states

        /*
        notes on possible bugged transitions:
        (logs from tests)
        IDLE to IDLE (maybe some redundancy)
        PREPARING to STOPPED (no intermediate play and completed states)
        STOPPED to COMPLETED (cant complete after stop)
        COMPLETED to IDLE (means a call to reset())
        missing log of transition from PLAYING to UNKNOWN (probably contructor called, other test)
        */
    }

    @Test
    public void resetAndPlay() throws IOException {

        AssetFileDescriptor file1 = getSingleAudioFileDescriptor(assetManager);
        AssetFileDescriptor file2;
        {
            String fileName2 = "Od.6.13-glaukopis.ogg";
            String filePath2 = Globals.getFilePath(fileName2,Globals.AUDIO_FILES_SUBFOLDER);
            file2 = getFileDescriptor(filePath2, assetManager);
        }

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(file1);
        audioPlayerHelper.play();
        audioPlayerHelper.changeAudioFiles(file2);
        audioPlayerHelper.play();
    }

    @Test
    public void resetWithFewerFilesOutOfBoundsException() throws IOException {

        //TODO FIXME this test still does not reproduce the error that was happening
        // error was in playNext(int trackIdx) with trackIdx out of bounds

        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(audioFiles);
        audioPlayerHelper.play();

        AssetFileDescriptor singleFile = getSingleAudioFileDescriptor(assetManager);

        audioPlayerHelper.changeAudioFiles(singleFile);
        audioPlayerHelper.play();


        audioPlayerHelper.changeAudioFiles(audioFiles);
        audioPlayerHelper.play();
    }

    @Test
    public void changeAudioFilesCheckState() throws IOException {
        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);
        AssetFileDescriptor singleFile
                = getSingleAudioFileDescriptor(assetManager);


        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                audioFiles);
        audioPlayerHelper.play();

        audioPlayerHelper.changeAudioFiles(singleFile);

        AudioPlayerHelper.PlayerState expectedPlayerState
                = AudioPlayerHelper.PlayerState.INITIALIZED;
        AudioPlayerHelper.PlayerState actualPlayerState
                = SharedTestUtils.getCurrentPlayerState(audioPlayerHelper);

        assertEquals("Wrong player state",expectedPlayerState,actualPlayerState);
    }

    @Test
    public void changeAudioFiles_checkFilesCount() throws IOException {
        AudioPlayerHelper player = getInitializedMP();
        assertEquals("files in media player",
                1,player._mediaPlayer.filesCount());

        AssetFileDescriptor[] assetFileDescriptors = getSomeAudioAssetFileDescriptors(assetManager);
        assertTrue(assetFileDescriptors.length > 1);

        player.changeAudioFiles(assetFileDescriptors);
        assertEquals("files in media player",
                assetFileDescriptors.length,player._mediaPlayer.filesCount());
    }

    @Test
    public void playNext() throws IOException {

        final String TAG = "playNext";

        //TODO
        // play, wait for player to complete, check that it plays other file
        AudioPlayerHelper player = getInitializedMP();
        // TODO choose two short audio files because we have to wait for end of play
        AssetFileDescriptor[] assetFileDescriptors = getSomeAudioAssetFileDescriptors(assetManager);
        player.changeAudioFiles(assetFileDescriptors);
        //player is initialized

        assertEquals("files in media player",
                assetFileDescriptors.length,player._mediaPlayer.filesCount());

        player._mediaPlayer.prepareAsync();//.prepare();
        assertEquals(PlayerState.PREPARING,player._mediaPlayer.getCurrentPlayerState());
        //NB: onPrepared listener starts playing automatically
        //TODO fix/reconsider all tests that use prepare/prepareAsynch in light of this

        assertEquals("files in media player",
                assetFileDescriptors.length,player._mediaPlayer.filesCount());

        Log.d(TAG,"waiting for first track to complete");
        waitForFirstTrackFinishPreparingAndPlaying(player);
        Log.d(TAG,"first track should have completed, waiting for track switch");
        waitForTrackSwitch(player);
        Log.d(TAG,"track switched, waiting for preparing");
        waitForNextTrackPreparing(player);
        Log.d(TAG,"second track prepared, waiting for playing..");
        waitForStateWhilePreparing(player,PlayerState.PLAYING);
    }

    @Test
    public void playWithNoFiles() throws IOException {

        //TODO add test for calling playnext(int trackIdx) when idx out of bounds exception
        // (error setting variable currentTrackIdx

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper();
        audioPlayerHelper.play();
    }

    @Test
    public void playSingleFile() throws IOException {

        //TODO test name is misleading

        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                audioFiles);
        audioPlayerHelper.play();

        int playAttempts = 15;//150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioPlayerHelper.close();
    }

    @Test
    public void playAfterStopped() throws IOException {

        AssetFileDescriptor singleFile
                = getSingleAudioFileDescriptor(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                singleFile);
        audioPlayerHelper.play();
        audioPlayerHelper.stop();
        audioPlayerHelper.play();
    }

    @Deprecated
    private static String getSingleAudioFilePath() {
        String singleAudioFileName = "Od.6.1-diosodisseus.ogg";
        String singleAudioFilePath = Globals.AUDIO_FILES_SUBFOLDER
                + "/" + singleAudioFileName;

        return singleAudioFilePath;
    }

    private static AssetFileDescriptor getSingleAudioFileDescriptor(
            AssetManager assetManager) {

        return getFileDescriptor(getSingleAudioFilePath(), assetManager);
    }

    private static AssetFileDescriptor getFileDescriptor(String path,
            AssetManager assetManager) {

        return Utils.getAssetFileDescriptor(path,
                assetManager);
    }

    private static AssetFileDescriptor[] getSomeAudioAssetFileDescriptors(
            AssetManager assetManager) {
        String[] paths = getSomeAudioFilesPaths();

        return Utils.getAssetFileDescriptors(paths,assetManager);
    }

    @Deprecated
    private static String[] getSomeAudioFilesPaths() {

        String[] audioFilePathsNames;

        //FIXME TODO add more files
        //"xwraf.ogg", "logosf.ogg", "nomosf.ogg"};
        String[] audioFileNames = new String[]{
                "Od.6.13-glaukopis.ogg","Od.6.24-glaukopis.ogg","Od.6.41-glaukopis.ogg",
                "Od.6.6-oispheas.ogg", "Od.6.8-ekasandron.ogg",
                "Od.6.9-anphideteikos.ogg",
                "Od.6.9-kaiedeimato.ogg",
                "Od.6.10-kaineus.ogg"};
        audioFilePathsNames = new String[audioFileNames.length];
        String audioFilesSubFolder = Globals.AUDIO_FILES_SUBFOLDER;

        int idx = 0;
        for(String fileName:audioFileNames) {
            audioFilePathsNames[idx] = audioFilesSubFolder + "/" + fileName;
            idx++;
        }

        return audioFilePathsNames;
    }

    //TODO test for trackIdx
    @Test
    public void audioFileTrackIdx() throws IOException {
        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                audioFiles);
        audioPlayerHelper.play();

        assertEquals("files count",audioFiles.length,
                audioPlayerHelper.filesCount());

        audioPlayerHelper.close();
    }

    @Test
    public void tryInsertFileIntoMediaplayer_IntegrityCheck() throws IOException{

        //get completed or stopped MP (so not idle)
        AudioPlayerHelper player = getStoppedMP();

        AssetFileDescriptor singleAudioFile
                = getSingleAudioFileDescriptor(assetManager);

        player._mediaPlayer.tryInsertFileIntoMediaplayer(singleAudioFile);

        assertFalse("Assets have been wrongly nullified, player can't play",
                Utils.isNullOrEmpty(player._mediaPlayer.assetFileDescriptors));

        player._mediaPlayer.changeAudioFiles(getSomeAudioAssetFileDescriptors(assetManager));
        player.play();
        waitForStateWhilePreparing(player,PlayerState.PLAYING);
    }

    //TODO FIXME lifecycle of when calling mp.reset()
    // it should be change files/reset than play
    // no calls to reset right after play

    @Test
    public void playMoreFiles() throws IOException {

        AssetFileDescriptor singleAudioFile
                = getSingleAudioFileDescriptor(assetManager);
        AssetFileDescriptor[] audioFiles
                = getSomeAudioAssetFileDescriptors(assetManager);

        AudioPlayerHelper audioPlayerHelper = new AudioPlayerHelper(
                audioFiles);
        audioPlayerHelper.play();

        int playAttempts = 15;//150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioFiles = new AssetFileDescriptor[]{singleAudioFile};
        audioPlayerHelper.changeAudioFiles(audioFiles);
        audioPlayerHelper.play();

        playAttempts = 15;//150000;
        while(playAttempts>0) {
            playAttempts--;
            audioPlayerHelper.play();
        }

        audioPlayerHelper.close();
    }
}