package com.francotte.go4lunch_opc;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.francotte.go4lunch_opc.ui.activities.LogActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LogActivityTest {

    @Rule
    public ActivityScenarioRule<LogActivity> activityRule =
            new ActivityScenarioRule<>(LogActivity.class);

    @Test
    public void test_isActivityInView() {
        onView(withId(R.id.activity_log_constraint_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_GoogleButton() {
        onView(withId(R.id.log_with_google)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_FacebookButton() {
        onView(withId(R.id.log_with_facebook)).check(matches(isDisplayed()));
    }

    @Test
    public void test_visibility_TwitterButton() {
        onView(withId(R.id.log_with_twitter)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isMainTitleTextDisplayed() {
        onView(withId(R.id.name_app))
                .check(matches(withText(R.string.go4lunch)));
    }

    @Test
    public void test_isDescriptionTextDisplayed() {
        onView(withId(R.id.description_app))
                .check(matches(withText(R.string.sub_title_log_activity)));
    }


}