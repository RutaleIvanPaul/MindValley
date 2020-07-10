package com.example.channels

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.channels.database.saveCategory
import com.example.channels.database.saveChannel
import com.example.channels.database.saveNewEpisode
import com.example.channels.views.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoadUITest {

    @Rule
    @JvmField
    var mainActivity: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun loadElements(){
        onView(withId(R.id.home)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.recyclerviewCategories))
            .perform(ViewActions.scrollTo())
            .check(matches(isDisplayed()))
        val recycler_view_newepisodes: RecyclerView = mainActivity.getActivity().findViewById(R.id.recyclerviewNewEpisodes)
        val recycler_view_channels: RecyclerView = mainActivity.getActivity().findViewById(R.id.recyclerviewChannels)

        assertEquals(6,recycler_view_newepisodes.adapter?.itemCount)
        assertEquals(6,recycler_view_channels.adapter?.itemCount)
    }

    @Test
    fun loadElementsOffline(){
        saveNewEpisode(
            appContext,
            "New Channel",
            "New Cover Asset",
            "New Title",
            "New Type"
        )

        saveCategory(appContext,"New Category")

        saveChannel(
            appContext,
            "New Channel",
            "New Cover Asset url",
            "New Icon asset url",
            "NewChannel id",
            2,
            "New Slug",
            "New Title",
            "New Series or not"
        )
        val testApp = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        testApp.openQuickSettings()
        checkNotNull(testApp.findObject(UiSelector().textContains("mode"))).click()
        testApp.pressBack()
        testApp.pressBack()
//        testApp.waitForIdle()

        Thread.sleep(5000)
        onView(withId(R.id.recyclerviewNewEpisodes)).check(matches(isDisplayed()))


    }



}