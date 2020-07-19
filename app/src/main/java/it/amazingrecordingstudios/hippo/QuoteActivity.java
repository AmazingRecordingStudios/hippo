package it.amazingrecordingstudios.hippo;

import androidx.appcompat.app.AppCompatActivity;

public class QuoteActivity extends AppCompatActivity {
/*
    // todo, at activity startup, get schermata with "audio test"
    // iterate the quotes, get the audio files from assets folder
    // play the ogg vorbis files
    OnSwipeTouchListener onSwipeTouchListener;

    //TODO add localization and screenshots, to give a glance
    // at the app in pictures https://gitlab.com/fdroid/fdroiddata/blob/master/CONTRIBUTING.md
    // https://f-droid.org/en/docs/All_About_Descriptions_Graphics_and_Screenshots/

    // ..removed stuff

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        // TODO get UI widgets to populate
        // ..
        // set event listeners on some widgets (play button, back and forward buttons)
        // add favourites button

        // ..removed stuff

        //TODO fix title not showing, fix translations not showing, fix playlists not playing

        // ..removed stuff

        //TODO handle activity getting closed by OS
        // saving the current screen, so can be reloaded at reopening
        // handle activity lifecycle
        // avoid activy replaying audio every time is app is reopened from background

        //..removed stuff

        // hippopotamos tagline: you don't find such phrases among the Romans
        //(queste frasi tra i Romani non le trovate)
        //in latino non ce ne sono di queste frasi
        //queste parole i Romani non ce le avevano
        //queste frasi i Romani non ce le avevano
        //questa letteratura i Romani non ce l'avevano
        //queste lettere i Romani non ce le avevano (tagline changes at every startup in italian)
        // (switches randomly among multiple translations)
        //queste frasi in latino non ci sono
        //(queste frasi non ci sono in latino)
        // (poi ripetere la tagline solo come commento alla frase dei due grammar books)

        //TODO
        // (unsure, because of user interface psychological effects, also I like the bare unmarked buttons)
        // remove buttons, only use swipes, and click on greek text to play it again

        //FIXME screen glitch on displaying the soft aspiration
        // also ῆ not displayed
        // on android 4.4.2 also ῖ not showing (all circumflex letters?)
        // try changing font
        //FIXME
        // title not displayed
        //FIXME
        // some playlits actually not shown in app (mythic quotes)
        //TODO
        // add option to disable autoplay, might be annoying when swiping quickly many quotes
        // (or disable it if two swipes in quick succession
        // better: don't start it immediately, give the user time to swipe. if he doesn't, start playing
        //TODO
        // add credit, wiki page with aika quote
        //TODO use new AudioPlayerHelper
        //TODO
        // release of mp with AudioPlayerHelper.close, would be only
        // when quitting activity
        // not when changing to other screen with other audio quotes

        //TODO ..might prepare in advance file descriptors for next screen

        //TODO: tweak quoteactivity to load and play the audio file if present
        //TODO: add check in quote activity, if file present, show toad if not
        //TODO: should the audio player throw exception or return message/boolean?

        //FIXME increase volume

        //TODO add counter in bottom left corner to know in which screen / playlist we are
        // i.e. 1 / 40
    }

    private void setLeftRightSwipeBehavior() {

        //TODO add swipe animation

        //View rootView = this.getCurrentFocus().getRootView();
        View rootView = findViewById(R.id.greekShortTextTV).getRootView();

        onSwipeTouchListener = new OnSwipeTouchListener(
                QuoteActivity.this) {
            @Override
            public void onSwipeLeft() {
                goNext();
            }

            @Override
            public void onSwipeRight() {
                goBack();
            }
        };

        rootView.setOnTouchListener(onSwipeTouchListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void goNext() {
        Schermata nextScreen = plItr.getNextScreen();
        refreshToScreen(nextScreen);
    }

    private void goBack() {
        Schermata prevScreen = plItr.getPrevScreen();
        refreshToScreen(prevScreen);
    }

    private Schermata getCurrentSchermata() {
        return this.plItr.getCurrentScreen();
    }

    @Deprecated
    private void loadAudioTestScreen() {
        final int ID_SCHEMATA_AUDIO_TEST = 14;
        final int ID_SOME_QUOTE = 1;
        Schermata audio_test = (Schermata) schermateById.get(ID_SCHEMATA_AUDIO_TEST);
        Quote some_quote = audio_test.getWordList().get(ID_SOME_QUOTE);
        String audioFileName = some_quote.getAudioFileName();

        this.currentAudioFilePath = audioFilesSubFolder + audioFileName;

        String singleAudioFileName = "hhmeraf.ogg";
        String[] audioFileNames = new String[]{"xwraf.ogg", "logosf.ogg", "nomosf.ogg"};
        String[] audioFilePathsNames = new String[audioFileNames.length];
        String singleAudioFilePath = audioFilesSubFolder + singleAudioFileName;

        int idx = 0;
        for(String fileName:audioFileNames) {
            audioFilePathsNames[idx] = audioFilesSubFolder + fileName;
            idx++;
        }

        //TODO implement pause if we click again on the (short/long) quote
        // that is playing
        // also maybe one more click to resume, double to start over/stop
        // and ..stop/changeAudioFiles if we click on the other quote while paused

        //TODO the two quotes to be put in the audioFilePathsNames are the
        // short and the long one. check if they exist.

        //TODO display a toast if clicking play and there is no file

        //this.mediaPlayer = AudioPlayUtils.setUpMediaPlayer();
        //AudioPlayUtils.playAudioFile(mediaPlayer, assetManager, currentAudioFilePath);

    }

    //..removed stuff*/
}
