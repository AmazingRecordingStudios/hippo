package it.amazingrecordingstudios.hippo;

import android.content.res.AssetFileDescriptor;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;

import it.amazingrecordingstudios.hippo.audioplayer.AudioPlayerHelper;
import it.amazingrecordingstudios.hippo.audioplayer.LoggableMediaPlayer;
import it.amazingrecordingstudios.hippo.audioplayer.MediaPlayerWrapperMultipleFiles;
import it.amazingrecordingstudios.hippo.audioplayer.SafeLoggableMediaPlayer;

import static org.junit.Assert.fail;

public class SharedMPTestUtils {

    public static final String TAG = "SharedMPTestUtils";

    public static void changeAudioFiles(AudioPlayerHelper audioPlayerHelper,
                                        AssetFileDescriptor[] assetFileDescriptors)
            throws IOException {
        MediaPlayerWrapperMultipleFiles player = getNonNullInnerPlayer(audioPlayerHelper);
        player.changeAudioFiles(assetFileDescriptors);
    }

    public static void tryPrepareAsynch(AudioPlayerHelper audioPlayerHelper) {
        MediaPlayerWrapperMultipleFiles player = getNonNullInnerPlayer(audioPlayerHelper);
        String currentMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        invokePlayerProtectedMethod(SafeLoggableMediaPlayer.class, player,currentMethodName);
    }

    public static void prepareAsync(AudioPlayerHelper audioPlayerHelper) {
        MediaPlayerWrapperMultipleFiles player = getNonNullInnerPlayer(audioPlayerHelper);
        String currentMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        invokePlayerProtectedMethod(LoggableMediaPlayer.class, player,currentMethodName);
    }

    public static void prepare(AudioPlayerHelper audioPlayerHelper) throws IOException {
        LoggableMediaPlayer player = getNonNullInnerPlayer(audioPlayerHelper);
        player.prepare();
    }

    public static void start(AudioPlayerHelper audioPlayerHelper) {
        MediaPlayerWrapperMultipleFiles player = getNonNullInnerPlayer(audioPlayerHelper);
        player.start();
    }

    public static void tryInsertFileIntoMediaplayer(AudioPlayerHelper audioPlayerHelper,
                                                    AssetFileDescriptor singleAudioFile) {
        MediaPlayerWrapperMultipleFiles player = getNonNullInnerPlayer(audioPlayerHelper);
        String currentMethodName = new Object(){}.getClass().getEnclosingMethod().getName();

        invokePlayerProtectedMethod(MediaPlayerWrapperMultipleFiles.class,
                player,currentMethodName,singleAudioFile);
    }


    public static Method getPlayerPrivateMethod(
            Class someClass,
            String methodName,
            Class<?>... parameterTypes) {
        try {
            Method method =
                    someClass.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        }
        catch (Exception e) {
            Log.e(TAG,e.toString());

            if(e.getClass() == NoSuchMethodException.class) {
                fail(e.toString());
            }
        }

        return null;
    }

    private static <C> Object invokePlayerProtectedMethod(
            Class<C> objClass,
            MediaPlayerWrapperMultipleFiles player,
            String methodName,
            Object... args) {

        Class[] argsClasses = new Class[args.length];
        for (int i=0; i<args.length; i++)
        {
            argsClasses[i] = args[i].getClass();
        }
        Method method = getPlayerPrivateMethod(objClass,
                methodName, argsClasses);

        try {
            return method.invoke(player, args);
        }
        catch (Exception e) {
            fail();
        }
        return null;
    }

    //NB: This is not synchronous
    public static void tryPutInErrorState(AudioPlayerHelper audioPlayerHelper) {
        try {
            audioPlayerHelper.close();
        } catch (IOException e) {
        }

        MediaPlayerWrapperMultipleFiles innerWrapperr = getInnerPlayer(audioPlayerHelper);

        try {
            innerWrapperr.prepare();
        } catch (Exception e) {
        }
    }

    private static MediaPlayerWrapperMultipleFiles getInnerPlayer(AudioPlayerHelper audioPlayerHelper){
        final String MPLAYER_FIELD_NAME = "_mediaPlayer";
        Class<AudioPlayerHelper> audioPlayerHelperClass
                = AudioPlayerHelper.class;

        MediaPlayerWrapperMultipleFiles player
                = SharedTestUtils.getFieldValue(
                audioPlayerHelperClass,audioPlayerHelper,MPLAYER_FIELD_NAME);

        return player;
    }

    private static MediaPlayerWrapperMultipleFiles getNonNullInnerPlayer(AudioPlayerHelper audioPlayerHelper){
        MediaPlayerWrapperMultipleFiles player = getInnerPlayer(audioPlayerHelper);

        if(player == null) {
            String msg = "getNonNullInnerPlayer, player is null";
            Log.e(TAG, msg);
            throw new IllegalStateException(TAG + msg);
        }
        else {
            return player;
        }
    }

    public static LoggableMediaPlayer.PlayerState getCurrentPlayerState(
            AudioPlayerHelper audioPlayerHelper) {
        SafeLoggableMediaPlayer player = getInnerPlayer(audioPlayerHelper);

        if(player != null) {
            return player.getCurrentPlayerState();
        }

        return null;
    }

    public static AssetFileDescriptor[] getAssetFileDescriptors(AudioPlayerHelper audioPlayerHelper) {
        MediaPlayerWrapperMultipleFiles player = getNonNullInnerPlayer(audioPlayerHelper);

        final String AssetFileDescriptors_FIELD_NAME = "assetFileDescriptors";
        Class<MediaPlayerWrapperMultipleFiles> mediaPlayerWrapperMultipleFilesClass
                = MediaPlayerWrapperMultipleFiles.class;

        AssetFileDescriptor[] assetFileDescriptors
                = SharedTestUtils.getFieldValue(
                mediaPlayerWrapperMultipleFilesClass,player,AssetFileDescriptors_FIELD_NAME);

        return assetFileDescriptors;
    }

    public static boolean hasCompletedPlaying(AudioPlayerHelper audioPlayerHelper) {
        SafeLoggableMediaPlayer player = getNonNullInnerPlayer(audioPlayerHelper);
        return player.hasCompletedPlaying();
    }

    public static boolean isIdle(AudioPlayerHelper audioPlayerHelper) {
        SafeLoggableMediaPlayer player = getNonNullInnerPlayer(audioPlayerHelper);
        return player.isIdle();
    }

    public static boolean isInitialized(AudioPlayerHelper audioPlayerHelper) {
        SafeLoggableMediaPlayer player = getNonNullInnerPlayer(audioPlayerHelper);
        return player.isInitialized();
    }

    public static boolean isPreparing(AudioPlayerHelper audioPlayerHelper) {
        SafeLoggableMediaPlayer player = getNonNullInnerPlayer(audioPlayerHelper);
        return player.isPreparing();
    }

    public static boolean isPlaying(AudioPlayerHelper audioPlayerHelper) {
        SafeLoggableMediaPlayer player = getNonNullInnerPlayer(audioPlayerHelper);
        return player.isPlaying();
    }

    public static int filesCount(AudioPlayerHelper audioPlayerHelper) {
        MediaPlayerWrapperMultipleFiles player = getNonNullInnerPlayer(audioPlayerHelper);
        return player.filesCount();
    }
}
