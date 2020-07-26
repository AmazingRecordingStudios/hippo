package it.amazingrecordingstudios.hippo.UITests;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.filters.Suppress;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import it.amazingrecordingstudios.hippo.MainActivity;
import it.amazingrecordingstudios.hippo.R;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    SharedUITestUtils sharedUITestUtils = new SharedUITestUtils();

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        AccessibilityChecks.enable();
    }

    @Suppress
    @Test
    public void playthroughDemo() {
        //TODO
    }

    @Suppress
    @Test
    public void playthroughPlaylistmenu() {
        //TODO
        //onView(...).check(matches(withText("Hello!")));
        //onView(...).perform(scrollTo(), click());
    }

    @Test
    @LargeTest
    public void playthrough() {
        onView(ViewMatchers.withId(R.id.startPlayingBtn)).perform(click());
        sharedUITestUtils.checkWeAreOnPlaylistMenu();

        onView(withText("Prepositions")).perform(click());
        sharedUITestUtils.checkPageFooter("1 of 2");
        onView(withId(R.id.pagerFragmanetConstraintLayout)).perform(swipeLeft());
        sharedUITestUtils.checkPageFooter("2 of 2");

        //FIXME contextual menu options not detected in espresso after swipe left
        //useGoBackMenuOption("2 of 2");
        Espresso.pressBack();
        sharedUITestUtils.checkPageFooter("1 of 2");
        Espresso.pressBack();

        sharedUITestUtils.checkWeAreOnPlaylistMenu();

        //Return to main activity
        Espresso.pressBack();
        sharedUITestUtils.checkWeAreOnMainActivity();
    }

    @Test
    @LargeTest
    public void playDemo() {
        onView(withId(R.id.demoBtn)).perform(click());

        final int expectedPageCount = 12;
        String expectedPageFooter = "1 of " + expectedPageCount;
        onView(withId(R.id.pageCounterTV)).check(matches(withText(expectedPageFooter)));

        sharedUITestUtils.showContextualMenu(expectedPageFooter);
        sharedUITestUtils.clickMenuOption("Back");
        //From demo, back should go directly to MainActivity, as there is no playlist menu
        sharedUITestUtils.checkWeAreOnMainActivity();
    }

    public static Matcher<View> withPageNumber(final Matcher<View> matcher,
                                          final int pageNumber) {
        return new TypeSafeMatcher<View>() {
            int currentPageNumber = 1;

            @Override
            public void describeTo(Description description) {
                description.appendText("with pageNumber: ");
                description.appendValue(pageNumber);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentPageNumber++ == pageNumber;
            }
        };
    }
}
