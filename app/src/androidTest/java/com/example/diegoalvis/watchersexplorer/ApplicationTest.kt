package com.example.diegoalvis.watchersexplorer

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.equalToIgnoringCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ApplicationTest {
    @get:Rule
    val activity = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init() {
        Intents.init()

        onView(withId(R.id.searchView)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("java"), pressImeActionButton())

        Thread.sleep(5000)
    }

    @After
    fun finish() {
        Intents.release()
    }

    @Test
    fun testSearchRepositories() {
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.name))
            .check(matches(withText(equalToIgnoringCase("java"))))
    }

    @Test
    fun testSelectRepository() {
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.name)).perform(click())

        Thread.sleep(500)

        onView(withId(R.id.itemRepo)).check(matches(isDisplayed()))
        onView(withId(R.id.watcherList)).check(matches(isDisplayed()))
        onView(withRecyclerView(R.id.watcherList).atPositionOnView(0, R.id.name))
            .check(matches(isDisplayed()))
        onView(withRecyclerView(R.id.watcherList).atPositionOnView(0, R.id.avatar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testInfoScreen() {
        onView(withId(R.id.info)).perform(click())
        intended(hasComponent(InfoActivity::class.java.name))
    }
}