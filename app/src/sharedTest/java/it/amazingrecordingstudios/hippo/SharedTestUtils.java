package it.amazingrecordingstudios.hippo;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.TreeMap;

import it.amazingrecordingstudios.hippo.audioplayer.AudioPlayerHelper;
import it.amazingrecordingstudios.hippo.audioplayer.LoggableMediaPlayer;
import it.amazingrecordingstudios.hippo.audioplayer.LoggableMediaPlayer.PlayerState;
import it.amazingrecordingstudios.hippo.audioplayer.MediaPlayerWrapperMultipleFiles;
import it.amazingrecordingstudios.hippo.audioplayer.SafeLoggableMediaPlayer;
import it.amazingrecordingstudios.hippo.database.QuotesProvider;
import it.amazingrecordingstudios.hippo.model.Playlist;
import it.amazingrecordingstudios.hippo.model.Quote;
import it.amazingrecordingstudios.hippo.model.Schermata;
import it.amazingrecordingstudios.hippo.utils.Utils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SharedTestUtils {

    public static final String TAG = "SharedTestUtils";

    public static void checkSchermate(TreeMap<Integer, Schermata> schermate,
                                int extectedMinNumSchermate, int expectedMax) {

        boolean enoughSchermate = schermate.size() >= extectedMinNumSchermate;

        String failMessageSchermate = "Expected " + extectedMinNumSchermate
                + " schermate, found " + schermate.size();
        assertTrue(failMessageSchermate,enoughSchermate);

        boolean tooManySchermate = schermate.size() > expectedMax;

        String failMsgTooManySchermate = "Expected max " + expectedMax
                + " schermate, found " + schermate.size();
        assertFalse(failMsgTooManySchermate,tooManySchermate);
    }


    public static void checkQuotes(TreeMap<Integer, Schermata> schermate,
                             int extectedMinNumQuotes, int expectedMax) {
        int quotesCount = 0;
        for(Schermata schermata:schermate.values()) {
            quotesCount += schermata.getWordList().size();
        }

        // a quote can be reused in more screens
        // so the quote count can be higher here
        boolean enoughQuotes = quotesCount >= extectedMinNumQuotes;

        String failMessageQuotes = "Expected " + extectedMinNumQuotes
                + " quotes, found " + quotesCount;
        assertTrue(failMessageQuotes,enoughQuotes);

        boolean tooManyQuotes = quotesCount > expectedMax;

        String failMsgTooManyQuotes = "Expected max " + expectedMax
                + " quotes, found " + quotesCount;
        assertFalse(failMsgTooManyQuotes,tooManyQuotes);
    }


    public static String getPrettifiedReadingList(Context appContext) {

        QuotesProvider quotesProvider = new QuotesProvider();
        quotesProvider.create(appContext);
        init(quotesProvider);

        TreeMap<Integer, Schermata> schermate
                = quotesProvider.getSchermateById();
        TreeMap<Integer, Playlist> playlists = quotesProvider.getPlaylistsByRank();

        StringBuilder sb = new StringBuilder();
        for(Playlist pl : playlists.values()) {

            if(!pl.isDisabled()) {
                appendPlaylistToStringBuilder(sb, pl);
            }
        }

        return sb.toString();
    }


    private static void appendPlaylistToStringBuilder(StringBuilder sb,
                                                      Playlist pl) {
        final String gitHubHeadings = "### ";
        final String quoteStart = "> **";
        final String boldEnd = "**";
        appendLineToStringBuilder(sb, gitHubHeadings + pl.getDescription());

        TreeMap<Integer, Schermata> rankedSchermate = pl.getRankedSchermate();

        for (Integer schermataRank : rankedSchermate.keySet()) {
            Schermata currentSchermata = rankedSchermate.get(schermataRank);

            appendEmptyLineToStringBuilder(sb);

            appendLineToStringBuilder(sb, currentSchermata.getTitle());
            //appendLineToStringBuilder(sb, currentSchermata.getDescription());

            String fullQuoteText = getFullQuoteAsString(currentSchermata);
            String shortQuoteText = getShortQuoteAsString(currentSchermata);
            String quoteToUse = fullQuoteText;
            if (Utils.isNullOrEmpty(fullQuoteText)) {

                if (!Utils.isNullOrEmpty(shortQuoteText)) {
                    quoteToUse = shortQuoteText;
                } else {
                    quoteToUse = getSinglelineHtmlWordList(currentSchermata);
                }

            }
            appendLineToStringBuilder(sb, quoteStart
                    + quoteToUse
                    + boldEnd);

            // TODO (in DB manager and queries) translation by selected user language
            appendLineToStringBuilder(sb, currentSchermata.getTranslation());
            appendLineToStringBuilder(sb, currentSchermata.getCitation());
            appendLineToStringBuilder(sb,
                    "Linguistic/grammar notes: ",
                    currentSchermata.getLinguisticNotes());
            appendLineToStringBuilder(sb, currentSchermata.getEasterEggComment());
            //TODO? add some other note apt for the reading list reader
        }
    }

    public static void appendLineToStringBuilder(StringBuilder sb,
                                                 String preamble,
                                                 String lineCore) {
        final String doubleNewLine = "\n\n";

        if(lineCore != null) {
            sb.append(preamble + lineCore);
            sb.append(doubleNewLine);
        }
    }

    public static void appendLineToStringBuilder(StringBuilder sb, String str) {

        appendLineToStringBuilder(sb, "", str);
    }

    public static void appendEmptyLineToStringBuilder(StringBuilder sb) {

        appendLineToStringBuilder(sb, "<br />");
    }

    public static String getShortQuoteAsString(Schermata screen) {
        return getQuoteAsString(screen.getShortQuote());
    }

    public static String getFullQuoteAsString(Schermata screen) {
        return getQuoteAsString(screen.getFullQuote());
    }

    public static String getQuoteAsString(Quote quote) {
        String string = "";
        if(!Utils.isNullOrEmpty(quote)) {
            string = quote.getQuoteText();
        }

        return string;
    }

    public static String getSinglelineHtmlWordList(Schermata screen) {
        return Schermata.getWordListAsString(screen.getWordList(),
                ", ", ".");
    }

    public static <C, F> F getFieldValue(Class<C> someClass,
                                         C someObject,
                                         String fieldName) {
        F value = null;

        try {
            Field field =
                    someClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            value = (F) field.get(someObject);
        }
        catch (Exception e) {
            Log.e(TAG,e.toString());
        }

        return value;
    }

    public static int getTableRowsCount(Context context, String tableName) {
        int rowsCount = -1;
        SQLiteOpenHelper mOpenHelper = SharedTestUtils.createDBOpenHelper(context);
        try(SQLiteDatabase db = mOpenHelper.getReadableDatabase()) {

            rowsCount = (int) DatabaseUtils.queryNumEntries(
                    db, tableName, null);
        } catch (Exception e) {
            Log.e(TAG,"Unable to count table rows " + e.toString());
        }

        return rowsCount;
    }

    public static int longForQuery(Context context,
                                   String query) {
        int rowsCount = -1;
        SQLiteOpenHelper mOpenHelper = SharedTestUtils.createDBOpenHelper(context);
        try(SQLiteDatabase db = mOpenHelper.getReadableDatabase()) {

            rowsCount = (int) DatabaseUtils.longForQuery(
                    db, query, null);
        } catch (Exception e) {
            Log.e(TAG,"Unable to count table rows " + e.toString());
        }

        return rowsCount;
    }

    public static SQLiteOpenHelper createDBOpenHelper(Context context) {
        return new QuotesProvider.DBHelper(context);
    }

    public static void init(QuotesProvider quotesProvider) {
        init(quotesProvider, QuotesProvider.DEFAULT_LANGUAGE);
    }

    public static void init(QuotesProvider quotesProvider,
                     QuotesProvider.Languages language) {
        quotesProvider.init(language,null);
    }

    private static MediaPlayerWrapperMultipleFiles getInnerPlayer(AudioPlayerHelper audioPlayerHelper){
        final String MPLAYER_FIELD_NAME = "_mediaPlayer";
        Class<AudioPlayerHelper> audioPlayerHelperClass
                = AudioPlayerHelper.class;

        MediaPlayerWrapperMultipleFiles player
                = getFieldValue(
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

    public static PlayerState getCurrentPlayerState(
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
                = getFieldValue(
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
}