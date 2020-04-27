package com.example.nikecodechallenge.view

import android.widget.AutoCompleteTextView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.nikecodechallenge.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class MainActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testSearchInput() {
        onView(ViewMatchers.withId(R.id.search_action))
            .perform((click()))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("diaf"), ViewActions.pressImeActionButton())

        closeSoftKeyboard()

        Thread.sleep(3000)
    }
}