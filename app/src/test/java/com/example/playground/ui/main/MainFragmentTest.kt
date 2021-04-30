package com.example.playground.ui.main

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.playground.MainActivity
import com.example.playground.TestApplication
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.AttributeSetBuilder
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class MainFragmentTest {

    private lateinit var context: Context
    private lateinit var attrs: AttributeSetBuilder

    val activity = ActivityScenario.launch(MainActivity::class.java)
    private val mainFragment = MainFragment()

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        attrs = Robolectric.buildAttributeSet()
    }

    @Test
    fun `when click home button`(){

    }


}