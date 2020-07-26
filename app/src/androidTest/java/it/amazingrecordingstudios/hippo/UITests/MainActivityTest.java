package it.amazingrecordingstudios.hippo.UITests;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.amazingrecordingstudios.hippo.MainActivity;
import it.amazingrecordingstudios.hippo.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void playthroughDemo() {
        //TODO
    }

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
        checkWeAreOnPlaylistMenu();

        onView(withText("Prepositions")).perform(click());
        checkPageFooter("1 of 2");
        onView(withId(R.id.pagerFragmanetConstraintLayout)).perform(swipeLeft());
        checkPageFooter("2 of 2");

        //FIXME contextual menu options not detected in espresso after swipe left
        //useGoBackMenuOption("2 of 2");
        Espresso.pressBack();
        checkPageFooter("1 of 2");
        Espresso.pressBack();

        checkWeAreOnPlaylistMenu();

        //Return to main activity
        Espresso.pressBack();
        checkWeAreOnMainActivity();
    }

    void checkPageFooter(String expectedFooter) {
        onView(allOf(withId(R.id.pageCounterTV), withText(expectedFooter)))
                .check(matches(withText(expectedFooter)));
    }

    void checkWeAreOnMainActivity() {
        // we are not on playlist menu
        onView(withText("Prepositions")).check(doesNotExist());
        onView(withText("Words, declensions")).check(doesNotExist());

        //we are not in a quote fragment
        onView(withId(R.id.pageCounterTV)).check(doesNotExist());

        onView(withId(R.id.demoBtn)).check(matches(withText("Demo")));
        onView(withId(R.id.startPlayingBtn)).check(matches(withText("Start playing")));
    }

    void checkWeAreOnPlaylistMenu() {
        //we are not in a quote fragment
        onView(withId(R.id.pageCounterTV)).check(doesNotExist());

        //and we are not on main activity
        onView(withId(R.id.demoBtn)).check(doesNotExist());
        onView(withId(R.id.startPlayingBtn)).check(doesNotExist());

        onView(withText("Prepositions")).check(matches(withText("Prepositions")));
        onView(withText("Words, declensions")).check(matches(withText("Words, declensions")));
    }

    void useGoBackMenuOption(String pageFooterText) {
        // Show the contextual menu
        //onView(withId(R.id.pageCounterTV)).perform(click());
        onView(allOf(withId(R.id.pageCounterTV), withText(pageFooterText))).perform(click());

        // Click on the Back item.
        //FIXME inconsistent sometimes espresso does not see the menu items
        onView(withText("Back")).perform(click());
    }

    @Test
    @LargeTest
    public void goBackMenuOption() {
        onView(ViewMatchers.withId(R.id.startPlayingBtn)).perform(click());
        checkWeAreOnPlaylistMenu();

        onView(withText("Prepositions")).perform(click());
        checkPageFooter("1 of 2");
        useGoBackMenuOption("1 of 2");

        // Verify that we have really clicked on the icon by
        // checking we are in previous activity
        checkWeAreOnPlaylistMenu();

        //Return to main activity
        Espresso.pressBack();
        checkWeAreOnMainActivity();
    }

    @Test
    @LargeTest
    public void playDemo() {
        onView(withId(R.id.demoBtn)).perform(click());

        final int expectedPageCount = 12;
        String expectedPageFooter = "1 of " + expectedPageCount;
        onView(withId(R.id.pageCounterTV)).check(matches(withText(expectedPageFooter)));

        useGoBackMenuOption(expectedPageFooter);
        //From demo, back should go directly to MainActivity, as there is no playlist menu
        checkWeAreOnMainActivity();
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
