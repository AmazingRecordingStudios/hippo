package it.amazingrecordingstudios.hippo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeMap;

import it.amazingrecordingstudios.hippo.SharedDBTestUtils;
import it.amazingrecordingstudios.hippo.model.Quote;
import it.amazingrecordingstudios.hippo.model.Schermata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//TODO FIXME TEST: when increasing db version (in code constant), throws exception
// doing, in onCreate, db.setForeignKeyConstraintsEnabled(true);

//TODO now that setForeignKeyConstraintsEnabled, run a statement that violates FK to check
// if it is enforced

//TODO fixme: db is actually not being updated when incrementing db version

//FIXME BUG TEST: screen not shown if table quotes_in_schermate is not used

public class DBManagerTest {

    private Context appContext;
    private QuotesProvider quotesProvider;

    @Before
    public void setUp() throws Exception {

        this.appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        this.quotesProvider = new QuotesProvider();
        this.quotesProvider.create(appContext);
        SharedDBTestUtils.init(this.quotesProvider);

        quotesProvider.getSchermateById();

        //this.quotesProvider.dropTables(this.appContext);
    }


    @After
    public void tearDown() throws Exception {

        if(this.quotesProvider == null) {//FIXME dbManager instances lifecycle
            this.appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            this.quotesProvider = new QuotesProvider();
            this.quotesProvider.create(appContext);
        }
        //this.quotesProvider.dropTables(this.appContext);
        this.quotesProvider.close();
    }

    @Ignore("Not implemented")
    @Suppress
    @Test
    public void deleteExistingDatabase() {

        //TODO add test in case db is alredy open but old version
        // was giving error when try to open in read mode, was locked

    }

    @Test@Suppress
    public void openDatabaseReadonly() {
    }

    @Test@Suppress
    public void createDBFromSqlFile() {
    }

    @Test
    public void getSchermate() {

        TreeMap<Integer, Schermata> schermate = getSchermateUtil();

        int extectedMinNumSchermate = 27;
        int extectedMinNumQuotes = 32;
        int maxSchermate = 100;
        int maxQuotes = 100;
        checkSchermate(schermate,extectedMinNumSchermate, maxSchermate);
        checkQuotes(schermate,extectedMinNumQuotes,maxQuotes);
    }

    private TreeMap<Integer, Schermata> getSchermateUtil() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        this.quotesProvider = new QuotesProvider();
        this.quotesProvider.create(appContext);
        SharedDBTestUtils.init(this.quotesProvider);

        return quotesProvider.getSchermateById();
    }

    @Test
    public void getShortAndFullQuote() {

        TreeMap<Integer, Schermata> schermate = getSchermateUtil();

        Schermata firstScreen = schermate.get(5);
        Quote firstShortQuote = firstScreen.getShortQuote();
        Quote firstFullQuote = firstScreen.getFullQuote();

        String expected = "Οὐραν<CASE>ὸς</CASE> πρῶτ<CASE>ος</CASE>";
        assertEquals("wrong firstShortQuote", expected, firstShortQuote.getQuoteText());

        expected = "Οὐραν<CASE>ὸς</CASE> πρῶτ<CASE>ος</CASE> τοῦ παντὸς ἐδυνάστευσε κόσμου.";
        assertEquals("wrong firstFullQuote", expected, firstFullQuote.getQuoteText());
    }

    @Test@Ignore("Consider rewriting the test, ..ensure a change on sql files is reflected on db")
    public void checkFreshResetDBHasNoRows() {

        //TODO FIXME make a test trying to load a first sql data insertion file
        // then a second one; encure that only the data from the second one is on the db

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<String> schemaStatements
                = DBUtils.getSchemaCreationStatementsFromSqlFile(appContext.getAssets());

        String dbPath = appContext.getDatabasePath(quotesProvider.DB_NAME).getPath();
        SQLiteDatabase db
                = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

        //this.quotesProvider.dropTables(this.appContext);
        //quotesProvider.dropTablesHelper(appContext, db);
        //quotesProvider.execSchemaCreationQueries(db,schemaStatements);

        // check schermate, quotes are empty
        TreeMap<Integer, Schermata> schermate = getSchermateUtil();
        checkSchermate(schermate,0, 0);
        checkQuotes(schermate,0,0);
    }

    private void checkSchermate(TreeMap<Integer, Schermata> schermate,
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

    private void checkQuotes(TreeMap<Integer, Schermata> schermate,
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

    @Test
    public void allScreensArePresentNoErrorsInViewQueries() {

        int expectedScreenCount
                = SharedDBTestUtils.getTableRowsCount(appContext,
                "schermate");

        int actualScreenCount = quotesProvider.getSchermateById().size();

        assertEquals("Wrong screen count in QuoteProvider",
                expectedScreenCount,actualScreenCount);

        String countView2RowsQuery
                = "SELECT COUNT(*) FROM (" +
                "SELECT DISTINCT s_id FROM v_schermate_and_quotes);";
        int actualScreenCount2
                = SharedDBTestUtils.longForQuery(appContext,countView2RowsQuery);

        assertEquals("Wrong screen count from view",
                expectedScreenCount,actualScreenCount2);
    }

    @Test
    public void getEasterEggComments() {

        QuotesProvider.Languages ENG = QuotesProvider.Languages.EN;

        TreeMap<Integer, String> easterEggComments = quotesProvider.getEasterEggComments(ENG);

        int min_num_eec = 1;
        boolean min_eec = easterEggComments.size() >= min_num_eec;

        assertTrue(min_eec);
    }

    @Test
    public void getLinguisticNotes() {

        QuotesProvider.Languages ENG = QuotesProvider.Languages.EN;
        TreeMap<Integer, String> linguisticNotes = quotesProvider.getLinguisticNotes(ENG);

        int min_num_ln = 5;
        boolean min_ln = linguisticNotes.size() >= min_num_ln;

        assertTrue(min_ln);
    }

    @Test@Suppress
    public void onCreate() {

        // TODO test, this was not being called after DB Manager instance creation
        // so in the DB Manager construction had to call db creation from sql file
    }

    @Test@Suppress
    public void onUpgrade() {
    }
}