package com.example.playground.extensions

import android.content.Context
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.example.playground.base.EspressoActions
import com.example.playground.base.EspressoMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

private fun first(expected: Matcher<View?>): Matcher<View?>? = object : TypeSafeMatcher<View?>() {
    private var first = false

    override fun matchesSafely(item: View?): Boolean {
        return if (expected.matches(item) && !first) {
            true.also { first = it }
        } else false
    }

    override fun describeTo(description: Description) {
        description.appendText("Matcher.first( $expected )")
    }
}

private fun getFirstViewById(id: Int) = onView(first(withId(id)))

private fun getResourceString(id: Int): String {
    val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    return targetContext.resources.getString(id)
}

fun Int.checkHasText(resId: Int) {
    checkHasText(getResourceString(resId))
}

fun Int.checkHasText(text: String) {
    getFirstViewById(this).check(ViewAssertions.matches(EspressoMatchers.withText(text)))
}

fun Int.checkHint(resId: Int) = this.also {
    checkHint(getResourceString(resId))
}

fun Int.checkHint(hint: String) = this.also {
    getFirstViewById(this).check(ViewAssertions.matches(EspressoMatchers.withHint(hint)))
}

fun Int.applyText(text: String) = this.also {
    getFirstViewById(this).perform(EspressoActions.applyText(text))
}

fun Int.getResource(): String? = InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(this)

fun Int.checkIsDisplayed(): Int = this.also {
    getFirstViewById(this).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun waitFor(delay: Long): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription(): String = "wait for $delay milliseconds"
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}