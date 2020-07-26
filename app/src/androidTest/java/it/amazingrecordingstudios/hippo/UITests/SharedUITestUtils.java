package it.amazingrecordingstudios.hippo.UITests;

import it.amazingrecordingstudios.hippo.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

public class SharedUITestUtils {

    public void useGoBackMenuOption(String pageFooterText) {
        // Show the contextual menu
        //onView(withId(R.id.pageCounterTV)).perform(click());
        onView(allOf(withId(R.id.pageCounterTV),
                withText(pageFooterText))).perform(click());

        // Click on the Back item.
        //FIXME inconsistent sometimes espresso does not see the menu items
        onView(withText("Back")).perform(click());
    }

    void checkPageFooter(String expectedFooter) {
        onView(allOf(withId(R.id.pageCounterTV), withText(expectedFooter)))
                .check(matches(withText(expectedFooter)));
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

    void checkWeAreOnMainActivity() {
        // we are not on playlist menu
        onView(withText("Prepositions")).check(doesNotExist());
        onView(withText("Words, declensions")).check(doesNotExist());

        //we are not in a quote fragment
        onView(withId(R.id.pageCounterTV)).check(doesNotExist());

        onView(withId(R.id.demoBtn)).check(matches(withText("Demo")));
        onView(withId(R.id.startPlayingBtn)).check(matches(withText("Start playing")));
    }
}
