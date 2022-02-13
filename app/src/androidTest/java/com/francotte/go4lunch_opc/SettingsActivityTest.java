package com.francotte.go4lunch_opc;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import com.francotte.go4lunch_opc.ui.activities.SettingsActivity;

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
public class SettingsActivityTest {

    @Rule
    public ActivityScenarioRule<SettingsActivity> activityRule =
            new ActivityScenarioRule<>(SettingsActivity.class);

    @Test
    public void test_isAppBarDisplayed() {
        onView(withId(R.id.main_activity_appbar)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isNotificationTextDisplayed() {
        onView(withId(R.id.settings_activity_title_notification))
                .check(matches(withText(R.string.settings_activity_text_notification)));
    }

    @Test
    public void test_isAccountTextDisplayed() {
        onView(withId(R.id.settings_activity_title_account))
                .check(matches(withText(R.string.settings_activity_text_account)));
    }

    @Test
    public void test_isNameUserTextDisplayed() {
        onView(withId(R.id.settings_activity_name_user_account))
                .check(matches(withText("rodolphe francotte")));
    }

    @Test
    public void test_isEmailUserTextDisplayed() {
        onView(withId(R.id.settings_activity_email_user_account))
                .check(matches(withText("rodolphefrancotte18@gmail.com")));
    }

}
