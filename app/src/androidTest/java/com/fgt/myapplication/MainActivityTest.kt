package com.fgt.myapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.fgt.myapplication.R.id.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)
//
    @Test
    fun testRecyclerViewBehaviour() {
    Thread.sleep(3000)
    onView(withId(club_list)).check(matches(isDisplayed()))
    onView(withId(club_list)).perform(
        RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
            7
        )
    )
    onView(withId(club_list)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            10,
            click()
        )
    )
}
}
