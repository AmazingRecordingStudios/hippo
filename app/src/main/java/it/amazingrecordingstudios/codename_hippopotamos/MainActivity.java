package it.amazingrecordingstudios.codename_hippopotamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.amazingrecordingstudios.codename_hippopotamos.database.AsyncResponse;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String TAG = "MainActivity";

    //TODO we don't need the screens here, but probably will need data from db
    // for main title quote and translation

    Button demoBtn;
    Button startPlayingBtn;
    TextView greekMainTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greekMainTitleTV = findViewById(R.id.greekMainTitle);

        this.demoBtn = this.findViewById(R.id.demoBtn);
        this.demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runDemo();
            }
        });

        this.startPlayingBtn = this.findViewById(R.id.startPlayingBtn);
        this.startPlayingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlaylistsActivity();
            }
        });

        //this.createDB();
        // TODO get chosen language from shared preferences
        //TODO ensure this is done asynch, not on UI thread
        /*this.quotesProvider = new QuotesProvider();
        this.quotesProvider.create(this);
        quotesProvider.init(QuotesProvider.Languages.EN);
        schermate = quotesProvider.getSchermateById();
        */
    }

    @Override
    protected void onPause() {
        super.onPause();

        //this.quotesProvider.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //schermate = quotesProvider.getSchermateById();
    }

    public void processFinish(Boolean wasCopySuccessful){

        /*
        if(wasCopySuccessful) {

            Utils.shortToast(this, "Copy database success");
            Log.v(TAG, "Copy database success, opening quote activity..");
            this.openQuoteActivity();
        }
        else
        {
            Utils.shortToast(this, "Copy database ERROR");
            Log.v(TAG, "Copy database ERROR");
        }
         */
    }

    void runDemo() {
        //TODO
        // load playlist "Recorded quotes"
        Intent intent = new Intent(MainActivity.this,
                QuotePagerActivity.class);
        intent.setAction(QuotePagerActivity.DEMO_ACTION);
        startActivity(intent);
    }

    void openQuoteActivity() {

        Intent intent = new Intent(MainActivity.this, QuoteActivity.class);

        //startActivityForResult(intent, QUOTE_ACTIVITY);

        //FIXME probably a bug to do another activity start
        //startActivity(intent);

        //FIXME reported issue that double back button give blank screen
        // probably is the returning into main activity, which is empty
    }

    @Deprecated
    void openSlideActivity() {

        //TODO we should tell the activity which playlists to run
        // or we should specify an action an then the activity retirieves
        // the playlists
        // ACTION_DEMO
        // ACTION_START_PLAYBACK
        // "The action, if given, must be listed by the component as one it handles."

        //TODO split in two methods, one for demo mode
        // getting demo playlist and starting the QuoteFragment on that
        //

        Intent intent = new Intent(MainActivity.this,
                QuotePagerActivity.class);
        intent.setAction(QuotePagerActivity.PLAY_ACTION);
        startActivity(intent);
    }

    void openPlaylistsActivity() {
        Intent intent = new Intent(MainActivity.this,
                PlaylistsActivity.class);
        startActivity(intent);
    }
}
