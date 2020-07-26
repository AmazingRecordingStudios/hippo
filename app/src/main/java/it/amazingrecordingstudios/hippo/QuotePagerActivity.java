package it.amazingrecordingstudios.hippo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import java.io.IOException;
import java.util.TreeMap;

import it.amazingrecordingstudios.hippo.audioplayer.AudioPlayerHelper;
import it.amazingrecordingstudios.hippo.database.QuotesProvider;
import it.amazingrecordingstudios.hippo.model.Schermata;
import it.amazingrecordingstudios.hippo.ui.screenslidepager.QuoteFragment;
import it.amazingrecordingstudios.hippo.ui.screenslidepager.QuoteViewModel;

public class QuotePagerActivity extends FragmentActivity {

    //TODO FIXME there is too much delay before opening the demo/playlist activity

    public static final String TAG = "QuotePagerActivity";
    public static final String DEMO_ACTION = "it.amazingrecordingstudios.hippo.DEMO";
    public static final String PLAY_ACTION = "it.amazingrecordingstudios.hippo.PLAY";

    public static final String DEMO_PLAYLIST_NAME = "Recorded quotes";
    public static final String PLAYLIST_NAME_EXTRA_KEY = "playlistName";

    private QuoteViewModel mViewModel;
    private TreeMap<Integer, QuoteFragment> quoteFragmentByPage;
    private TreeMap<Integer, Boolean> pageWasSelectedAtLeastOnce;
    private int previouslySelectedPage;

    public AudioPlayerHelper getAudioPlayer() {
        return audioPlayer;
    }

    private AudioPlayerHelper audioPlayer;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager2;
    private ViewPager2.OnPageChangeCallback viewPager2PageChangeCallback;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slide_pager_activity);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager2 = findViewById(R.id.pager);
        this.quoteFragmentByPage = new TreeMap<>();
        this.pageWasSelectedAtLeastOnce = new TreeMap<>();
        this.previouslySelectedPage = -1;

        //TODO here in choosing the adapter, make it according to the intent action
        // i.e. demo or startplaying
        String action = this.getIntent().getAction();
        Log.d(TAG,"Action: " + action);

        QuotesProvider.Languages languageSetting = QuotesProvider.DEFAULT_LANGUAGE;

        if(action == DEMO_ACTION) {
            getData(languageSetting, DEMO_PLAYLIST_NAME);
        } else if(action == PLAY_ACTION) {

            String playlistName
                    = this.getIntent().getExtras().getString(PLAYLIST_NAME_EXTRA_KEY);

            getData(languageSetting, playlistName);
        } else {
            Log.e(TAG,"No action specified");
            throw new IllegalStateException("No intent action specified");
            //TODO default action?
        }

        this.audioPlayer = new AudioPlayerHelper();

        pagerAdapter = new QuotePagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);

        viewPager2PageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                boolean pageWasSelectedAtLeastOnceBefore = false;

                if(!pageWasSelectedAtLeastOnce.containsKey(position)) {
                    pageWasSelectedAtLeastOnce.put(position, true);
                } else {
                    pageWasSelectedAtLeastOnceBefore = true;
                }

                if(previouslySelectedPage != -1) {
                    quoteFragmentByPage.get(previouslySelectedPage).onNoLongerSelected();
                }
                quoteFragmentByPage.get(position).onSelected(
                        pageWasSelectedAtLeastOnceBefore);
                previouslySelectedPage = position;
            }
        };
        viewPager2.registerOnPageChangeCallback(viewPager2PageChangeCallback);
    }

    public void putQuoteFragment(int position,
                                 QuoteFragment quoteFragment) {
        this.quoteFragmentByPage.put(position, quoteFragment);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //TODO implement close/reopen audio player when activity destroyed,
        // closed and reStarted
        //audio player should be created by activity and passed in fragment
        // constructor
        //onStart, resume, pause, stop, destroy (close?)
        // implement player pause/stop when swiping fragments?
        Log.d(TAG,"Activity stopped, stopping media player if it's playing or paused");
        if(this.audioPlayer.isPlayingOrPaused()) {
            //TODO check if there are other states in which
            // we should stop the player, like preparing, prepared
            this.audioPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            this.audioPlayer.close();
        } catch (IOException e) {
            Log.e(TAG,e.toString());
        }

        viewPager2.unregisterOnPageChangeCallback(
                viewPager2PageChangeCallback);
    }

    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)  {

        if(keyCode == KeyEvent.KEYCODE_MENU) {

            showOptionsMenuInCurrentFragment();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    private void showOptionsMenuInCurrentFragment() {
        int currentPage = viewPager2.getCurrentItem();
        this.quoteFragmentByPage.get(currentPage).showPopupOptionsMenu();
    }

    public int getScreenCount() {
        return this.mViewModel.getScreenCount();
    }

    private void getData(QuotesProvider.Languages language) {
        getData(language, null);
    }

    private void getData(QuotesProvider.Languages language,
                         String playlistDescriptor) {
        ViewModelProvider viewModelProvider = ViewModelProviders.of(this);
        this.mViewModel
                = viewModelProvider.get(QuoteViewModel.class);

        this.mViewModel.init(language, playlistDescriptor);
    }

    private class QuotePagerAdapter extends FragmentStateAdapter {

        QuotePagerActivity fragActivity;

        public QuotePagerAdapter(QuotePagerActivity fragActivity) {
            super(fragActivity);

            this.fragActivity = fragActivity;
        }

        @Override
        public Fragment createFragment(int position) {

            //TODO
            // code here for switching quote
            // Return a NEW fragment instance in createFragment(int)
            Log.d(TAG,"Creating quoteFragment at " + position);

            Schermata screen = this.fragActivity.mViewModel.getScreenAt(position);

            Log.d(TAG,"Fragment with screen: " + screen.toString());
            QuoteFragment fragment = QuoteFragment.newInstance(
                    position, screen,
                    this.fragActivity.mViewModel.getScreenCount(),
                    fragActivity.getAudioPlayer(),
                    fragActivity.getAssets());
            Bundle args = new Bundle();
            args.putInt(QuoteFragment.SCREEN_ID_BUNDLE_FIELD, position + 1);
            fragment.setArguments(args);

            this.fragActivity.putQuoteFragment(position, fragment);

            return fragment;
        }

        @Override
        public int getItemCount() {
            return this.fragActivity.getScreenCount();
        }

        //TODO handle event onactivity end to release media player

    }
}
