package it.amazingrecordingstudios.hippo.UITests;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.filters.Suppress;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.amazingrecordingstudios.hippo.PlaylistsActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PlaylistsActivityTest {

    SharedUITestUtils sharedUITestUtils = new SharedUITestUtils();

    @Rule
    public ActivityScenarioRule<PlaylistsActivity> quotePagerActivityTestRule =
            new ActivityScenarioRule<>(PlaylistsActivity.class);

    @Before
    public void setUp() {
        AccessibilityChecks.enable();
    }

    @Suppress
    @Test
    public void doNotShowDisabledPlaylistOnMenu() {

        //TODO rewrite this test with Espresso or Roboelectric
        /*
        QuotesProvider quotesProvider = new QuotesProvider();

        Context appContext;

        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        quotesProvider.create(appContext);
        quotesProvider.init(QuotesProvider.DEFAULT_LANGUAGE, null);
        TreeMap<Integer, Playlist> playlistByRank = quotesProvider.getPlaylistsByRank();
        SharedDBTestUtils.init(quotesProvider);
        quotesProvider.getSchermateById();

        List<Map<String, String>> playlistsNamesData
                = PlaylistsActivity.getPlaylistsNamesDataHelper(playlistByRank);

        ArrayList<String> plNames = new ArrayList<>();
        for(Map<String, String> plEntry:playlistsNamesData) {
            String plName = plEntry.get(QuotePagerActivity.PLAYLIST_NAME_EXTRA_KEY);
            plNames.add(plName);
        }

        for(Playlist pl:playlistByRank.values()) {
            if(pl.isDisabled()) {
                assertFalse(plNames.contains(pl.getDescription()));
            } else {
                assertTrue(plNames.contains(pl.getDescription()));
            }
        }*/
    }

    @Test
    @LargeTest
    public void goBackMenuOption() {

        onView(withText("Prepositions")).perform(click());

        sharedUITestUtils.checkPageFooter("1 of 2");
        sharedUITestUtils.useGoBackMenuOption("1 of 2");

        // Verify that we have really clicked on the icon by
        // checking we are in previous activity
        sharedUITestUtils.checkWeAreOnPlaylistMenu();
    }
}