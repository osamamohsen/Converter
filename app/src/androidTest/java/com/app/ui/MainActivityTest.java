package com.app.ui;

import com.app.R;
import com.app.utiles.espresso.EspressoIdlingResource;
import com.app.utiles.espresso.RecyclerViewMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
//         unnecessary, just to double check the correct implementation of DeviceAnimationTestRule library
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }


    @Test
    public void testCaseForRecyclerViewClick() {
        for (int i = 1; i < 32; i++) {
            onView(withId(R.id.rv_main))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
        }


    }
}