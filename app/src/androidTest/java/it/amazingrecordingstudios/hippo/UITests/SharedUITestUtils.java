package it.amazingrecordingstudios.hippo.UITests;

import it.amazingrecordingstudios.hippo.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.containsString;

public class SharedUITestUtils {

    public void showContextualMenu(String pageFooterText) {
        onView(allOf(withId(R.id.pageCounterTV),
                withText(pageFooterText))).perform(click());
        checkMenuIsDisplayed();
    }

    public void checkMenuIsDisplayed() {
        onView(allOf(withResourceName("title"), withText("Back"))).check(matches(withText("Back")));
        onView(allOf(withResourceName("title"), withText("Options"))).check(matches(withText("Options")));
        onView(allOf(withResourceName("title"), withText("Credits"))).check(matches(withText("Credits")));
        onView(allOf(withResourceName("title"), withText("About"))).check(matches(withText("About")));
    }

    public void clickMenuOption(String optionText) {
        onView(withText(optionText)).perform(click());
    }

    void checkPageFooter(String expectedFooter) {
        onView(allOf(withId(R.id.pageCounterTV), withText(expectedFooter)))
                .check(matches(withText(expectedFooter)));
    }

    void checkWeAreOnCreditsPage(){
        onView(withId(R.id.creditsTitle)).check(matches(withText("Credits")));
    }

    void checkWeAreOnAboutPage(){
        onView(withId(R.id.aboutTV)).check(matches(withText(containsString("Diet"))));
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
