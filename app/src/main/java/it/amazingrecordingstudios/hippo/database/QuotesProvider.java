package it.amazingrecordingstudios.hippo.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import it.amazingrecordingstudios.hippo.model.Playlist;
import it.amazingrecordingstudios.hippo.model.Quote;
import it.amazingrecordingstudios.hippo.model.Schermata;
import it.amazingrecordingstudios.hippo.utils.Utils;

public class QuotesProvider {

    //DB Manager notes:
    // TODO for the audio files, not stored into the db, check how to have them in the sd card only
    // i.e. by downloading them
    // TODO check if there is a default "app data" dir in the sd card, or path conventions
    // TODO make unit/integration tests

    // TODO check how to handle/synch/check db version here and in the db created from sql file
    // TODO

    //FIXME this failed on device, " unknown error (code 14): Could not open database"
    // SQLiteDatabase db = tryOpenDB(path,cf,flags);
    // seems fixed now, apparently was because of the static methods creating the db

    //TODO check if close causes errors if reopening

    // ensureDBOpen
    //TODO check the need for this, add tests. was giving error
    // when db closed
    // when db file manually deleted before runnning tests

    //End of DB Manager notes

    //FIXME sorting of screens in a playlist; sorting field seems to have no effect
    //TODO add test for this

    public static final int DATABASE_VERSION = 84;
    public static final String DB_NAME = "greekquotes";
    public static final String TAG = "QuotesProvider";

    public final static Languages DEFAULT_LANGUAGE = Languages.EN;
    public enum Languages {
        //TODO check if numeric values cna be explicity assigned
        //TODO to ensure they matche the db
        NO_LANGUAGE, // = 0
        EN, // = 1
        IT // = 2
    }

    public static final String CREDIT_FIELD_NAME = "credit";

    public static class DBHelper extends SQLiteOpenHelper {

        private static final String TAG = "DBHelper";
        private final Context myContext; //TODO check if final is necessary

        public DBHelper(Context context) {//todo, should constructor by public?
            super(context, DB_NAME, null, DATABASE_VERSION);

            Log.v(TAG,"Creating SQLiteOpenHelper for " + DB_NAME + " version " + DATABASE_VERSION);

            this.myContext = context;
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);

            Log.v(TAG,"Setting setForeignKeyConstraintsEnabled true");
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.v(TAG, "onCreate, db version: " + db.getVersion());

            AssetManager assetManager = myContext.getAssets();
            ArrayList<String> schemaStatements
                    = DBUtils.getSchemaCreationStatementsFromSqlFile(assetManager);
            TreeMap<Integer,String> dataInsertStatements
                    = DBUtils.getSingleLineSqlStatementsFromInputStream(
                    assetManager, DBUtils.DATA_INSERT_SQL_FILE);

            execSchemaCreationQueries(db, schemaStatements);
            insertDataIntoDB(db,dataInsertStatements);
        }

        private static void insertDataIntoDB(
                SQLiteDatabase db,
                TreeMap<Integer,String> dataInsertStatements) {

            for(int i=0;i<dataInsertStatements.size();i++) {
                String statement = dataInsertStatements.get(i);
                try {
                    db.execSQL(statement);
                } catch (Exception e) {
                    Log.e(TAG, statement);
                    Log.e(TAG, e.toString());
                    throw e;
                }
            }
            Log.v(TAG, "data inserted");
        }

        private static void execSchemaCreationQueries(SQLiteDatabase myDatabase,
                                                     ArrayList<String> schemaStatements) {
            for (String statement : schemaStatements) {
                //Log.v(TAG,statement);
                myDatabase.execSQL(statement);
                //Log.v(TAG,DBUtils.getConcatTableNames(myDatabase));
            }
            Log.v(TAG, "schema created");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            Log.v(TAG, "DB has been opened");

            //TODO get data from DB
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion);

            dropTables(db, myContext);
            onCreate(db);
        }

        public void dropTables(SQLiteDatabase db, Context context) {

            Log.w(TAG,"dropping DB tables..");

                    AssetManager assetManager = context.getAssets();
            TreeMap<Integer,String> dropSchemaStatements
                    = DBUtils.getSingleLineSqlStatementsFromInputStream(
                    assetManager,
                    DBUtils.DROP_SCHEMA_SQL_FILE);

            for(int i=0;i<dropSchemaStatements.size();i++) {
                String statement = dropSchemaStatements.get(i);
                try {
                    db.execSQL(statement);
                } catch (Exception e) {
                    Log.e(TAG, statement);
                    Log.e(TAG, e.toString());
                    throw e;
                }
            }
            Log.v(TAG, "Dropped DB schema and data.");
        }
    }

    Languages languageSetting;
    private DBHelper mOpenHelper;
    private TreeMap<Integer, Schermata> schermate;
    public TreeMap<Integer,Playlist> getPlaylistsByRank() {
        return playlistsByRank;
    }
    private TreeMap<Integer,Playlist> playlistsByRank;

    public ArrayList<String> getCredits() {
        return credits;
    }
    private ArrayList<String> credits;

    public void create(Context context) {
        //TODO: ensure schema creation is not lenghty operation or make it asynch
        //only called from the application main thread,
        // and must avoid performing lengthy operations.
        // See the method descriptions for their expected thread behavior.
        mOpenHelper = new DBHelper(context);
    }

    public void init(Languages language, String playlistDescriptor) {
        setLanguageSetting(language);

        getDataFromDB();

        if(!Utils.isNullOrEmpty(playlistDescriptor)) {
            //TODO make a test for this to ensure that we don't pass the wrong
            // playlist descritpion and get none
          Playlist pl = filterPlaylist(playlistDescriptor, this.playlistsByRank);

          this.setPlaylistsByRank(pl);
        }
    }

    public String getAboutText() {

        final String getAboutTextQuery = "SELECT note FROM app_notes WHERE title='about';";

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        String aboutText = DatabaseUtils.stringForQuery(
                db,getAboutTextQuery, null);
        return aboutText;
    }

    private void setLanguageSetting(Languages language) {
        this.languageSetting = language;
    }

    private void setPlaylistsByRank(Playlist pl) {
        this.playlistsByRank.clear();

        if(pl != null) {
            int firstToPlayRank = 0;
            this.playlistsByRank.put(firstToPlayRank, pl);
        }
    }

    public static Playlist filterPlaylist(String playlistDescription,
                                       TreeMap<Integer,Playlist> playlists) {

        Log.v(TAG, "Filtering from " + playlists.size() + " playlists");
        for(Playlist pl:playlists.values()) {
            Log.v(TAG, "Filtering playlist: " + pl.getDescription());

            if(pl.getDescription().equals(playlistDescription)) {
                return pl;
            }
        }

        Log.e(TAG,"Filter resulted in no playlist");
        return null;
    }

    /*public boolean onCreate() {
        //mOpenHelper = new DBHelper(getContext());
        return false;
    }*/

    private void getDataFromDB() {

        // todo, translations languages
        // TODO some fields use a default language if the preferred one is absent

        //myCreateDBFromSqlFile();
        //openDatabaseReadonly();

        this.credits = getCreditsFromDB(this.mOpenHelper);

        TreeMap<Integer, Schermata> newSchermate = new TreeMap<Integer, Schermata>();
        TreeMap<Integer, Quote> newAllQuotes = new TreeMap<Integer, Quote>();

        TreeMap<Integer, String> linguisticNotes = getLinguisticNotes(this.languageSetting);
        TreeMap<Integer, String> easterEggComments = getEasterEggComments(this.languageSetting);
        this.playlistsByRank = getPlaylistsFromDB();

        // TODO rededish, review: problems with db creation,
        //  opening, state, and closing cycles
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        processGetAllQuotesQuery(db, newAllQuotes);
        processScreensAndQuotesQuery(db, newSchermate, linguisticNotes, easterEggComments);
        setShortAndFullQuotesInScreens(newAllQuotes, newSchermate);

        this.schermate = newSchermate;
        for(Playlist pl :this.playlistsByRank.values()) {
            pl.setSchermate(this.schermate);
        }

        //FIXME removing this db.close();//not needed anymore once data is loaded
    }

    public TreeMap<Integer, Schermata> getSchermateById() {

        return this.schermate;
    }


    private static void setShortAndFullQuotesInScreens(
            TreeMap<Integer, Quote> allQuotes,
            TreeMap<Integer, Schermata> allScreens) {

        for(Schermata screen: allScreens.values()) {
            if(null != screen.getShortQuoteId()) {
                Quote shortQuote = allQuotes.get(screen.getShortQuoteId());
                screen.setShortQuote(shortQuote);
            }
            if(null != screen.getFullQuoteId()) {
                Quote fullQuote = allQuotes.get(screen.getFullQuoteId());
                screen.setFulltQuote(fullQuote);
            }
        }
    }

    private static void processScreensAndQuotesQuery(
            SQLiteDatabase db,
            TreeMap<Integer, Schermata> newSchermate,
            TreeMap<Integer, String> linguisticNotes,
            TreeMap<Integer, String> easterEggComments) {
        String quotesAndSchermateQuery = "SELECT * FROM v_schermate_and_quotes";
        try(Cursor cursor = db.rawQuery(quotesAndSchermateQuery, null)) {
            // todo merge try

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                addQuoteAndSchermata(cursor,
                        newSchermate,
                        linguisticNotes,
                        easterEggComments);
                cursor.moveToNext();
            }
        }
        catch (SQLiteException sqle) {
            Log.e(TAG, sqle.toString());
            Log.e(TAG, "Tables: " + DBUtils.getConcatTableNames(db));
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private static void processGetAllQuotesQuery(
            SQLiteDatabase db,
            TreeMap<Integer, Quote> allQuotes
    ) {
        String quotesAndSchermateQuery = "SELECT * FROM greek_quotes";
        try(Cursor cursor = db.rawQuery(quotesAndSchermateQuery, null)) { // todo merge try

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                addQuote(cursor, allQuotes);
                cursor.moveToNext();
            }
        }
    }


    private static void addQuote(Cursor cursor,
                                 TreeMap<Integer, Quote> allQuotes) {

        final int greekQuoteIddColIdx = cursor.getColumnIndex("_id");
        final int quoteColIdx = cursor.getColumnIndex("quoteText");
        final int phoneticColIdx = cursor.getColumnIndex("phoneticTranscription");
        final int audioFileNameColIdx = cursor.getColumnIndex("audioFileName");

        int idQuote = cursor.getInt(greekQuoteIddColIdx);
        String greekQuote = cursor.getString(quoteColIdx);
        String phoneticTranscription = cursor.getString(phoneticColIdx);
        String audioFileName = cursor.getString(audioFileNameColIdx);

        Quote currentQuote = new Quote(idQuote, greekQuote,
                phoneticTranscription, audioFileName);
        allQuotes.put(idQuote, currentQuote);
    }

    private static void addQuoteAndSchermata(Cursor cursor,
                                             TreeMap<Integer,Schermata> schermate,
                                             TreeMap<Integer,String> linguisticNotes,
                                             TreeMap<Integer,String> easterEggComments) {

        final int schermataIdColIdx = cursor.getColumnIndex("s_id");
        final int greekQuoteIddColIdx = cursor.getColumnIndex("gq_id");
        final int quoteColIdx = cursor.getColumnIndex("quote");
        final int short_quote_idColIdx = cursor.getColumnIndex("short_quote_id");
        final int full_quote_idColIdx = cursor.getColumnIndex("full_quote_id");
        final int phoneticColIdx = cursor.getColumnIndex("phoneticTranscription");
        final int positionColIdx = cursor.getColumnIndex("position");
        final int titleColIdx = cursor.getColumnIndex("title");
        final int descriptionColIdx = cursor.getColumnIndex("description");
        final int defaultTranslationColIdx = cursor.getColumnIndex("default_translation");
        final int citColIdx = cursor.getColumnIndex("cit");
        final int audioFileNameColIdx = cursor.getColumnIndex("audioFileName");

        int idSchermata = cursor.getInt(schermataIdColIdx);
        int idQuote = cursor.getInt(greekQuoteIddColIdx);
        Integer shortQuoteId = getNullableInteger(cursor, short_quote_idColIdx);
        Integer fullQuoteId = getNullableInteger(cursor, full_quote_idColIdx);
        String greekQuote = cursor.getString(quoteColIdx);
        String phoneticTranscription = cursor.getString(phoneticColIdx);
        int quotePosition = cursor.getInt(positionColIdx);
        String title = cursor.getString(titleColIdx);
        String description = cursor.getString(descriptionColIdx);
        String cit = cursor.getString(citColIdx);
        String audioFileName = cursor.getString(audioFileNameColIdx);

        String linguisticNote = linguisticNotes.get(idSchermata);
        String eeComment = easterEggComments.get(idSchermata);

        // NB schermata translation is used in place of those of each quote
        //TODO change this on the basis of user language preferences
        String defaultTranslation = cursor.getString(defaultTranslationColIdx);
        //Log.v(TAG,"Translation: " + defaultTranslation);

        Quote currentQuote = new Quote(idQuote, quotePosition, greekQuote,
                phoneticTranscription, audioFileName);

        Schermata currentSchermata = schermate.get(idSchermata);
        if(null == currentSchermata) {

            currentSchermata= new Schermata(
                    idSchermata,
                    title,
                    description,
                    shortQuoteId,
                    fullQuoteId,
                    defaultTranslation,
                    linguisticNote,
                    cit,
                    eeComment);
            schermate.put(idSchermata, currentSchermata);
        }
        currentSchermata.addWordToList(currentQuote);
    }

    private static Integer getNullableInteger(Cursor cursor, int colIdx) {

        Integer value = null;
        boolean colIsNull = Cursor.FIELD_TYPE_NULL == cursor.getType(colIdx);
        if(!colIsNull) {
            value = cursor.getInt(colIdx);
        }

        return value;
    }

    private static Integer getNullableInteger(Cursor cursor, String colName) {

        return getNullableInteger(cursor, cursor.getColumnIndex(colName));
    }

    private static ArrayList<String> getCreditsFromDB(SQLiteOpenHelper dbOpenHelper) {
        ArrayList<String> creditsTmp = new ArrayList<>();

        final String CREDITS_TABLE = "credits";

        try(SQLiteDatabase db = dbOpenHelper.getReadableDatabase()) {

            String creditsQuery = "SELECT * FROM " + CREDITS_TABLE;
            Cursor cursor = db.rawQuery(creditsQuery, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                String credit = cursor.getString(cursor.getColumnIndex(CREDIT_FIELD_NAME));
                creditsTmp.add(credit);
                cursor.moveToNext();
            }
        }

        return creditsTmp;
    }

    private TreeMap<Integer, Playlist> getPlaylistsFromDB() {

        TreeMap<Integer,Playlist> playlistsByRank = new TreeMap<>();

        final String PLAYLISTS_T = "v_playlists";

        try(SQLiteDatabase db = mOpenHelper.getReadableDatabase()) {

            int playlistCount = (int) DatabaseUtils.queryNumEntries(
                    db,PLAYLISTS_T,null);

            String playlistsQuery = "SELECT * FROM " + PLAYLISTS_T;
            Cursor cursor = db.rawQuery(playlistsQuery, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                Integer playlistId = cursor.getInt(cursor.getColumnIndex("p_id"));
                String description = cursor.getString(cursor.getColumnIndex("description"));

                int playlistRank = getPlaylistRank(cursor,playlistCount,playlistId);

                int disabledAsInt = cursor.getInt(cursor.getColumnIndex("disabled"));
                boolean disabled = DBUtils.castSqliteBoolean(disabledAsInt);

                //TODO add test to ensure each screen is properly ranked within a playlsit
                String schermateConcat = cursor.getString(cursor.getColumnIndex("schermate"));
                String playOrderConcat = cursor.getString(cursor.getColumnIndex("sorting"));
                Log.v(TAG,"Screen ids: " + schermateConcat);
                Log.v(TAG,"Screen ranks: " + playOrderConcat);

                ArrayList<Integer> schermateIds = DBUtils.getIntsFromConcatString(schermateConcat);
                ArrayList<Integer> playOrderRanks = DBUtils.getIntsFromConcatString(playOrderConcat);

                TreeMap<Integer, Integer> singlePlAsScreenRanksById = new TreeMap<>();
                Iterator<Integer> schermateIdsItr = schermateIds.iterator();
                Iterator<Integer> playOrderRanksItr = playOrderRanks.iterator();
                while(schermateIdsItr.hasNext()) {
                    int keyScreenId = schermateIdsItr.next();
                    int valueScreenRank = playOrderRanksItr.next();

                    singlePlAsScreenRanksById.put(keyScreenId, valueScreenRank);
                    Log.v(TAG,"Rank " + valueScreenRank + " for screen " + keyScreenId);
                }

                Playlist currentPlaylist = new Playlist(description,
                        singlePlAsScreenRanksById,
                        disabled);

                playlistsByRank.put(playlistRank,currentPlaylist);
                cursor.moveToNext();
            }
        }

        return playlistsByRank;
    }

    private static int getPlaylistRank(Cursor cursor, int playlistsCount, int playlistId) {

        Integer rankInDB = getNullableInteger(cursor,"playlist_rank");
        int rank;

        if(rankInDB == null) {
            rank = playlistsCount + playlistId;
        } else {
            rank = rankInDB;
        }
        return rank;
    }

    public  TreeMap<Integer, String> getEasterEggComments(QuotesProvider.Languages language) {

        // TODO use eeComment in the default language when it's the only one

        TreeMap<Integer, String> eeComments = new TreeMap<Integer, String>();

        try(SQLiteDatabase db = mOpenHelper.getReadableDatabase()) {

            String eeCommentsQuery = "SELECT * FROM easter_egg_comments WHERE language_id = "
                    + language.ordinal();
            Cursor cursor = db.rawQuery(eeCommentsQuery, null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                Integer schermataId = cursor.getInt(cursor.getColumnIndex("schermata_id"));
                String eeComment = cursor.getString(cursor.getColumnIndex("eeComment"));
                eeComments.put(schermataId, eeComment);

                cursor.moveToNext();
            }
        }

        return eeComments;
    }

    public TreeMap<Integer, String> getLinguisticNotes(QuotesProvider.Languages language) {

        TreeMap<Integer, String> linguisticNotes = new TreeMap<Integer, String>();

        try(SQLiteDatabase db = mOpenHelper.getReadableDatabase()) {

            String linguisticNotesQuery = "SELECT * FROM linguistic_notes WHERE language_id = "
                    + language.ordinal();
            Cursor cursor = db.rawQuery(linguisticNotesQuery, null);

            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                Integer schermataId = cursor.getInt(cursor.getColumnIndex("schermata_id"));
                String linguisticNote = cursor.getString(cursor.getColumnIndex("linguisticNote"));

                linguisticNotes.put(schermataId, linguisticNote);

                cursor.moveToNext();
            }
        }
        return linguisticNotes;
    }



    public synchronized void close() {
        //super.close();
        mOpenHelper.close();
    }
}
