package it.amazingrecordingstudios.codename_hippopotamos.UITests;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.Suppress;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.amazingrecordingstudios.codename_hippopotamos.QuotePagerActivity;
import it.amazingrecordingstudios.codename_hippopotamos.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class QuotePagerActivityTest {

    //TODO test a screen with all values, check that are displayed
    //TODO test a screen with empty/null values: quotes, notes, counter,
    // check that it doesn't crash
    // Display Main Activity, About, Credits, Playlists activities, check
    // that text is displayed and don't crash
    // checks for fonts?

    @Rule
    public ActivityScenarioRule<QuotePagerActivity> quotePagerActivityActivityTestRule =
            new ActivityScenarioRule<>(QuotePagerActivity.class);

    @Suppress
    @Test
    public void testViewPagerSwipeFunctionality()
            throws InterruptedException{

        //onView(withId(R.id.changeTextBt)).perform(click());
        //onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));
        onView(ViewMatchers.withId(R.id.pager)).perform(swipeLeft());

    }
}
