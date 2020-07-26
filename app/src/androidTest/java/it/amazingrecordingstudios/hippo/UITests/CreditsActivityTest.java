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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static org.hamcrest.Matchers.anything;

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
    public void accessibilityChecks() {
        //TODO find a way to check all the items in the list view,
        // also in a more efficient way (like not doing anything on it)
        // than performing a click
        onData(anything())
                .inAdapterView(withContentDescription("Credits"))
                .atPosition(3)
                .perform(click());
    }
}
