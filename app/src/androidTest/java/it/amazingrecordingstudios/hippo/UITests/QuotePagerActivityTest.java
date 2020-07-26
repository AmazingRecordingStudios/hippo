package it.amazingrecordingstudios.hippo.UITests;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.Suppress;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.amazingrecordingstudios.hippo.QuotePagerActivity;
import it.amazingrecordingstudios.hippo.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;

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
            new ActivityScenarioRule<>(createDemoIntent());

    private Intent createDemoIntent() {
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, QuotePagerActivity.class);
        intent.setAction(QuotePagerActivity.DEMO_ACTION);
        return intent;
    }

    @Before
    public void setUp() {
        AccessibilityChecks.enable();
    }

    @Suppress
    @Test
    public void testViewPagerSwipeFunctionality()
            throws InterruptedException{

        //onView(withId(R.id.changeTextBt)).perform(click());
        //onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));
        onView(ViewMatchers.withId(R.id.pager)).perform(swipeLeft());
    }

    //val scenario = activityScenarioRule.scenario
    @Test
    public void accessibilityMinWidgetSize48p()
            throws InterruptedException{
        //onView(withId(R.id.demoBtn)).perform(click());
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
        //onView(withId(R.id.changeTextBt)).perform(click());
        //onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));

        onView(ViewMatchers.withId(R.id.pageCounterTV)).perform(click());
    }
}
