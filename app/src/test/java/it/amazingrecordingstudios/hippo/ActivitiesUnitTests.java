package it.amazingrecordingstudios.hippo;

//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//@RunWith(RobolectricTestRunner.class)
//@RunWith(AndroidJUnit4.class)
public class ActivitiesUnitTests {

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
        //}
    }

    @Test
    public void useAppContext() {

        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //assertEquals("it.amazingrecordingstudios.hippo", appContext.getPackageName());

        /*try(ActivityScenario<MainActivity> scenario
                    = ActivityScenario.launch(MainActivity.class)) {
            /*scenario.onActivity(activity -> {
                //assertThat(activity.greekMainTitleTV.getText()).isEqualTo("something");
            });*/
        //}

    }
}
