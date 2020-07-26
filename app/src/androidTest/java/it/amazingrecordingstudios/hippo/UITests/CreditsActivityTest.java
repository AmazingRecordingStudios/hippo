package it.amazingrecordingstudios.hippo.UITests;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.amazingrecordingstudios.hippo.CreditsActivity;
import it.amazingrecordingstudios.hippo.MainActivity;
import it.amazingrecordingstudios.hippo.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class CreditsActivityTest {

    @Rule
    public ActivityScenarioRule<CreditsActivity> creditsActivityScenarioRule =
            new ActivityScenarioRule<>(CreditsActivity.class);

    @Before
    public void setUp() {
        AccessibilityChecks.enable();
    }

    @Test
    public void emptyTest() {
        //TODO perform some event to do accessibility checks
        //onView(ViewMatchers.withId(R.id.pageCounterTV)).perform(click());
    }
}
