package it.amazingrecordingstudios.hippo;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.TreeMap;

import it.amazingrecordingstudios.hippo.database.QuotesProvider;
import it.amazingrecordingstudios.hippo.model.Playlist;
import it.amazingrecordingstudios.hippo.model.Schermata;
import it.amazingrecordingstudios.hippo.utils.Utils;

import it.amazingrecordingstudios.hippo.SharedTestUtils.*;

public class SharedDBTestUtils {

    public static final String TAG = "SharedTestUtils";

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
        SharedTestUtils.appendLineToStringBuilder(sb, gitHubHeadings + pl.getDescription());

        TreeMap<Integer, Schermata> rankedSchermate = pl.getRankedSchermate();

        for (Integer schermataRank : rankedSchermate.keySet()) {
            Schermata currentSchermata = rankedSchermate.get(schermataRank);

            SharedTestUtils.appendEmptyLineToStringBuilder(sb);

            SharedTestUtils.appendLineToStringBuilder(sb, currentSchermata.getTitle());
            //appendLineToStringBuilder(sb, currentSchermata.getDescription());

            String fullQuoteText = SharedTestUtils.getFullQuoteAsString(currentSchermata);
            String shortQuoteText = SharedTestUtils.getShortQuoteAsString(currentSchermata);
            String quoteToUse = fullQuoteText;
            if (Utils.isNullOrEmpty(fullQuoteText)) {

                if (!Utils.isNullOrEmpty(shortQuoteText)) {
                    quoteToUse = shortQuoteText;
                } else {
                    quoteToUse = SharedTestUtils.getSinglelineHtmlWordList(currentSchermata);
                }

            }
            SharedTestUtils.appendLineToStringBuilder(sb, quoteStart
                    + quoteToUse
                    + boldEnd);

            // TODO (in DB manager and queries) translation by selected user language
            SharedTestUtils.appendLineToStringBuilder(sb, currentSchermata.getTranslation());
            SharedTestUtils.appendLineToStringBuilder(sb, currentSchermata.getCitation());
            SharedTestUtils.appendLineToStringBuilder(sb,
                    "Linguistic/grammar notes: ",
                    currentSchermata.getLinguisticNotes());
            SharedTestUtils.appendLineToStringBuilder(sb, currentSchermata.getEasterEggComment());
            //TODO? add some other note apt for the reading list reader
        }
    }

    public static int getTableRowsCount(Context context, String tableName) {
        int rowsCount = -1;
        SQLiteOpenHelper mOpenHelper = createDBOpenHelper(context);
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
        SQLiteOpenHelper mOpenHelper = createDBOpenHelper(context);
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
}
