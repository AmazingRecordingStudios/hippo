package it.amazingrecordingstudios.hippo;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import it.amazingrecordingstudios.hippo.database.AsyncResponse;
import it.amazingrecordingstudios.hippo.database.QuotesProvider;


public class CopyFileTask extends AsyncTask<CopyFileTask.Task, Integer, Boolean> {

    private static final String TAG = "CopyFileTask";

    enum Task {
        CREATE_DB_FROM_SQL_FILE,
        COPY_DB_ASSET_FILE
    }

    public AsyncResponse delegate;
    AssetManager assetManager;
    Context context;
    SQLiteDatabase sqLiteDatabase;

    public CopyFileTask(AsyncResponse delegate,
                        AssetManager assetManager,
                        Context context,
                        SQLiteDatabase database) {

        this.delegate = delegate;
        this.assetManager = assetManager;
        this.context = context;
        this.sqLiteDatabase = database;
    }

    protected Boolean doInBackground(Task... params) {

        Task myTast = params[0];

        Boolean taskSuccessful = false;

        switch (myTast) {

            case COPY_DB_ASSET_FILE:
                taskSuccessful = copyDbAssetFile();
                break;
            case CREATE_DB_FROM_SQL_FILE:
                taskSuccessful = createDbFromSqlFile();
                break;
        }

        return taskSuccessful;
    }

    private Boolean createDbFromSqlFile() {

        QuotesProvider quotesProvider = new QuotesProvider();
        quotesProvider.create(this.context);
        quotesProvider.init(QuotesProvider.Languages.EN, null);
        quotesProvider.getSchermateById();
        return true;
    }

    @Deprecated
    private Boolean copyDbAssetFile() {

        final String DB_LOCATION = "/data/data" +
                "/it.amazingrecordingstudios.hippo" +
                "/databases/";
        String outputFileName = DB_LOCATION + QuotesProvider.DB_NAME;

        boolean wasCopySuccessful = false;

        // TODO check if there is enough space in the SD card to copy the db

        try(InputStream dbAssetFileInputStream = assetManager.open(QuotesProvider.DB_NAME);
            OutputStream outputStream = new FileOutputStream(outputFileName)) {


            byte[] buffer = new byte[1024];
            int lastBytesReadCount = 0;
            final int END_OF_FILE_CODE = -1;

            while (lastBytesReadCount != END_OF_FILE_CODE) {
                lastBytesReadCount = dbAssetFileInputStream.read(buffer);

                if(lastBytesReadCount != END_OF_FILE_CODE) {
                    outputStream.write(buffer, 0, lastBytesReadCount);
                }
                else {
                    break;
                }

                // we could update progress here (percent of copied file)
            }

            outputStream.flush();
            wasCopySuccessful = true;
            Log.v(TAG, "DB Copied");
        }
        catch (Exception e) {
            Log.v(TAG, "Error: " + e.toString());
        }

        return wasCopySuccessful;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    @Override
    protected void onPostExecute(Boolean wasCopySuccessful) {

        //super.onPostExecute(result);

        delegate.processFinish(wasCopySuccessful);
    }
}
