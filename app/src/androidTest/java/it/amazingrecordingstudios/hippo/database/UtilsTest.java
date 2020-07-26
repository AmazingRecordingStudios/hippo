package it.amazingrecordingstudios.hippo.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import it.amazingrecordingstudios.hippo.SharedDBTestUtils;

import static org.junit.Assert.*;

public class UtilsTest {

    private static final String TAG = "UtilsTest";

    @Test
    public void getPrettifiedReadingList() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String readingList = SharedDBTestUtils.getPrettifiedReadingList(appContext);

        File txtFile;
        try {
            txtFile = File.createTempFile("reading-list", ".md");
            Log.v(TAG,"Created temp file: " + txtFile.getAbsolutePath());

            try(FileWriter txtFileFW = new FileWriter(txtFile)) {

                txtFileFW.write(readingList);
            }

        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }

    }

    @Test@Suppress
    public void getSchemaCreationStatementsFromSqlFile() {
    }

    @Test@Suppress
    public void getSingleLineSqlStatementsFromInputStream() {
    }


    @Test
    public void checkSqlFileCustomSeparator() {

        // checks that we haven't forgotten any "--/" separator
        // after any statement in the sql file

        // get statements
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<String> parsedSchemaStatements
                = DBUtils.getSchemaCreationStatementsFromSqlFile(appContext.getAssets());


        // parse file to check "CREATE" and "PRAGMA" occurrences
        int statementsCount = countStatementsInSchemaSqlFile();

        // count, should be same number
        assertEquals(statementsCount, parsedSchemaStatements.size());
    }


    private int countStatementsInSchemaSqlFile() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        int countStatements = 0;
        AssetManager assetManager = appContext.getAssets();
        try(InputStream shemaCreationSqlFileInputStream
                    = assetManager.open(DBUtils.SCHEMA_SQL_FILE)) {

            //creating an InputStreamReader object
            InputStreamReader isReader = new InputStreamReader(shemaCreationSqlFileInputStream);
            //Creating a BufferedReader object
            BufferedReader reader = new BufferedReader(isReader);
            final String createKeyword = "CREATE";
            final String pragmaKeyword = "PRAGMA";
            try {
                String str;
                while((str = reader.readLine())!= null){
                    if(str.contains(createKeyword)
                            || str.contains(pragmaKeyword)) {
                        countStatements++;
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
        catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        return countStatements;
    }
}