package com.example.playground.ui.instrument

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.playground.R
import com.example.playground.extensions.checkHasText
import com.example.playground.extensions.checkIsDisplayed
import com.example.playground.extensions.waitFor
import com.example.playground.ui.instrument.model.InstrumentModel
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class InstrumentFragmentTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var scenario: FragmentScenario<InstrumentFragment>
    private val instrumentViewModel: InstrumentViewModel = mockk(relaxed = true)

    private val instrumentModel = InstrumentModel(
        name = "Les Paul Teste",
        type = InstrumentModel.Type.GUITAR,
        resId = R.drawable.guitarra_les_paul
    )

    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            androidContext(context)
            modules(
                module { viewModel { instrumentViewModel } }
            )
        }
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @After
    fun cleanUp() {
        unmockkAll()
    }

    @Test
    fun assert_Instrument_Model()  {

        R.id.progressInstrument
            .checkIsDisplayed()

        waitFor(2000)

        scenario.onFragment { fragment ->
            fragment.render(
                instrumentModel
            )
        }

        R.id.progressInstrument
            .checkIsDisplayed()

        R.id.tvName
            .checkIsDisplayed()
            .checkHasText(instrumentModel.name)

        waitFor(2000)

    }
}