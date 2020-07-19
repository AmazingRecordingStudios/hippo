package it.amazingrecordingstudios.codename_hippopotamos.UITests;

import android.app.Activity;
import android.view.View;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.Suppress;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import it.amazingrecordingstudios.codename_hippopotamos.MainActivity;
import it.amazingrecordingstudios.codename_hippopotamos.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    Activity currentActivity = null;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void playthrough() {
        onView(ViewMatchers.withId(R.id.startPlayingBtn)).perform(click());
        onView(withText("Prepositions")).perform(click());
        onView(withId(R.id.pagerFragmanetConstraintLayout)).perform(swipeLeft());
    }

    @Suppress@Ignore//@Test
    public void playDemo() {
        onView(withId(R.id.demoBtn)).perform(click());

        final int expectedPageCount = 12;

        //QuotePagerActivity quotePagerActivity = (QuotePagerActivity)getCurrentActivity();
        //assertThat(quotePagerActivity.getScreenCount(), is(equalTo(expectedPageCount)));

        /*ActivityScenario.ActivityAction<QuotePagerActivity> someAction
                = new ActivityScenario.ActivityAction<QuotePagerActivity>() {
            @Override
            public void perform(QuotePagerActivity activity) {
                //startActivity(Intent(activity, MyOtherActivity::class.java))
                activity.getScreenCount();
                assertThat(activity.getScreenCount(), is(equalTo(expectedPageCount)));
            }
        };
        mainActivityTestRule.getScenario().onActivity(someAction);*/

        int pagerFragment = R.id.pagerFragmanetConstraintLayout;

        /*for(int currentPage=1; currentPage<=expectedPageCount; currentPage++) {
            String expectedPageCounterText = currentPage + " of " + expectedPageCount;
            //onView(withId(R.id.pageCounterTV)).check(matches(withText(expectedPageCounterText)));
            onView(withText(expectedPageCounterText)).check(matches(isDisplayed()));

            //androidx.test.espresso.PerformException: Error performing 'fast swipe' on view 'Animations or transitions are enabled on the target device.
            onView(withPageNumber(withId(pagerFragment), currentPage))
                    .perform(swipeLeft());
        }*/

        //onView(withPageNumber(withId(R.id.pageCounterTV), 2)).perform(click());
        //onView(withId(R.id.pageCounterTV)).check(matches(withText("2 of 12")));

    }

    /*
    private Activity getCurrentActivity() {
        final Activity[] activity = new Activity[1];
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                //com.android.internal.policy.DecorContext cannot be cast to android.app.Activity
                activity[0] = (Activity) view.getContext();
            }
        });
        return activity[0];
    }*/

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

    public Activity getCurrentActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                ActivityLifecycleMonitor activityLifecycleMonitor
                        = ActivityLifecycleMonitorRegistry.getInstance();
                Collection<Activity> resumedActivities =
                        activityLifecycleMonitor.getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }
}
