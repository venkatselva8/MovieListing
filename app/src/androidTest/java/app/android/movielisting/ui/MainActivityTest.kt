package app.android.movielisting.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.android.movielisting.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testToolbarTitle() {
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText(R.string.toolbar_title))))
    }

    @Test
    fun testSearchMenu() {
        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(androidx.appcompat.widget.SearchView::class.java))
            .check(matches(isDisplayed()))
    }
}
