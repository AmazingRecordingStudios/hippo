package it.amazingrecordingstudios.codename_hippopotamos.UITests;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.Suppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.amazingrecordingstudios.codename_hippopotamos.MainActivity;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ActivitiesTest {

    /*
    * @Test
@SdkSuppress(maxSdkVersion = 27)
public void testButtonClickOnOreoAndLower() {
    // ...
}

@Test
@SdkSuppress(minSdkVersion = 28)
public void testButtonClickOnPieAndHigher() {
    // ...
}

    * */

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityTestRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp()  {
        //MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        //Object scenario = launchActivity<MainActivity>();

        //assertThat(activity.greekMainTitleTV).isEqualTo("something");
    }

    @After
    public void tearDown() {
        /*try(ActivityScenario<MainActivity> scenario
                    = ActivityScenario.launch(MainActivity.class)) {
            /*scenario.onActivity(activity -> {
                //assertThat(activity.greekMainTitleTV.getText()).isEqualTo("something");
            });*/

            //scenario.close();
        //}


    }

    @Suppress
    @Test
    public void useAppContext() {

        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //assertEquals("it.amazingrecordingstudios.codename_hippopotamos", appContext.getPackageName());

        try(ActivityScenario<MainActivity> scenario
                    = ActivityScenario.launch(MainActivity.class)) {
            /*scenario.onActivity(activity -> {
                //assertThat(activity.greekMainTitleTV.getText()).isEqualTo("something");
            });*/
            scenario.moveToState(Lifecycle.State.CREATED);


            ActivityScenario.ActivityAction<MainActivity> someAction
                    = new ActivityScenario.ActivityAction<MainActivity>() {
                @Override
                public void perform(MainActivity activity) {
                    //startActivity(Intent(activity, MyOtherActivity::class.java))
                }
            };

            /*
            * //Labda expressions not supported at language level '7'
            * scenario.onActivity(activity -> {
                assertTrue(false);
            });
            * */

            //assertThat(activity.greekMainTitleTV.getText()).isEqualTo("something");

            scenario.onActivity(someAction);
            //val originalActivityState = scenario.state;
            scenario.recreate();

            //Retrieve activity results
            //onView(withId(R.id.finish_button)).perform(click())

            // This is a blocking call, and ActivityScenario doesn't
            // call finish() on the activity. If the activity under
            // test isn't finishing or finished, this method times out
            // and throws a runtime exception.
            Object resultCode = scenario.getResult().getResultCode();
            Object resultData = scenario.getResult().getResultData();

            /*
            * All methods within ActivityScenario are blocking calls,
            * so the API requires you to run them in the instrumentation thread.
            * To trigger actions in your activity under test, use Espresso
            * view matchers to interact with elements in your view:
            * */
            //onView(withId(R.id.refresh)).perform(click())

            //If you need to call a method on the activity itself, however, you can do so safely by implementing ActivityAction:
            /*
            * scenario.onActivity { activity ->
                  activity.handleSwipeToRefresh()
                }
        * */

        }

        ActivityScenarioRule<MainActivity> activityScenarioRule
                = new ActivityScenarioRule<>(MainActivity.class);
        activityScenarioRule.getScenario();
    }

    @Suppress
    @Test
    public void listGoesOverTheFold() {
        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }


}
