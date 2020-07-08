package com.example.channels

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.channels.database.*
import org.junit.After
import org.junit.Test

import org.junit.Before

import com.google.common.truth.Truth.assertThat

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SQLHelperTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setDB(){
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        databaseName = "test_database"
    }
    @Test
    fun insertData() {
        assertThat(
                saveCategory(instrumentationContext,"New Category")
                ).isGreaterThan(0)

        assertThat(
            saveNewEpisode(
                instrumentationContext,
                "New Channel",
                "New Cover Asset",
                "New Title",
                "New Type"
            )
        ).isGreaterThan(0)

        assertThat(
            saveChannel(
                instrumentationContext,
                "New Channel",
                "New Cover Asset url",
                "New Icon asset url",
                "NewChannel id",
                2,
                "New Slug",
                "New Title",
                "New Series or not"
            )
        ).isGreaterThan(0)
    }

    @Test
    fun returnData(){
        assertThat(
            returnCategory(instrumentationContext)
        ).isNotNull()

        assertThat(
            returnChannel(instrumentationContext)
        ).isNotNull()

        assertThat(
            returnNewEpisode(instrumentationContext)
        ).isNotNull()
    }

    @Test
    fun deleteData(){
        assertThat(
            clearNewEpisode(instrumentationContext)
        ).isNotNull()

        assertThat(
            clearCategory(instrumentationContext)
        ).isNotNull()

        assertThat(
            clearChannel(instrumentationContext)
        ).isNotNull()
    }

    @After
    fun unsetDB(){
        clearNewEpisode(instrumentationContext)
        clearCategory(instrumentationContext)
        clearChannel(instrumentationContext)
        databaseName = "channels"
    }
}