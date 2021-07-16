package com.example.playground.extensions

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.example.playground.base.EspressoActions
import com.example.playground.base.EspressoMatchers
import com.google.android.material.tabs.TabLayout
import com.example.playground.extensions.ClickType.SHORT
import com.example.playground.extensions.ClickType.LONG
import org.hamcrest.*

enum class ClickType {
    SHORT, LONG
}

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

private fun click(clickType: ClickType): ViewAction {
    return when (clickType) {
        LONG -> ViewActions.longClick()
        SHORT -> ViewActions.click()
    }
}

fun Int.click() = this.also {
    getFirstViewById(this).perform(ViewActions.click())
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

fun Int.getResource(): String? =
    InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(this)

fun Int.checkIsDisplayed(): Int = this.also {
    getFirstViewById(this).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun Int.checkIsNotDisplayed() = this.also {
    getFirstViewById(this).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
}

fun Int.checkIsEnabled() = this.also {
    getFirstViewById(this).check(ViewAssertions.matches(ViewMatchers.isEnabled()))
}

fun Int.checkIsNotEnabled() = this.also {
    getFirstViewById(this).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())))
}

fun Int.listHasItemCount(itemCount: Int?) {
    onView(withId(this)).check(hasItemCount(itemCount))
}

fun <T : RecyclerView.ViewHolder> Int.clickRecyclerViewItem(
    position: Int,
    clickType: ClickType = SHORT
) {
    onView(withId(this)).perform(actionOnItemAtPosition<T>(position, click(clickType)))
}

fun Int.clickToolbar() {
    onView(
        Matchers.allOf(
            CoreMatchers.instanceOf(AppCompatImageButton::class.java),
            ViewMatchers.withParent(withId(this))
        )
    ).perform(ViewActions.click())
}

fun Int.clickOnTabByIndex(index: Int) {
    onView(withId(this)).perform(selectTabAtPosition(index))
}

fun selectTabAtPosition(tabIndex: Int): ViewAction {
    return object : ViewAction {
        override fun getDescription() = "with tab at index $tabIndex"

        override fun getConstraints() = Matchers.allOf(
            ViewMatchers.isDisplayed(),
            ViewMatchers.isAssignableFrom(TabLayout::class.java)
        )

        override fun perform(uiController: UiController, view: View) {
            val tabLayout = view as TabLayout
            val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                ?: throw PerformException.Builder()
                    .withCause(Throwable("No tab at index $tabIndex"))
                    .build()

            tabAtIndex.select()
        }
    }
}

private fun hasItemCount(count: Int?) = ViewAssertion { view, noViewFoundException ->
    if (noViewFoundException != null) {
        throw noViewFoundException
    }

    if (view !is RecyclerView) {
        throw IllegalStateException("The asserted view is not RecyclerView")
    }

    if (view.adapter == null) {
        throw IllegalStateException("No adapter is assigned to RecyclerView")
    }

    ViewMatchers.assertThat("RecyclerView item count", view.adapter?.itemCount, CoreMatchers.equalTo(count))
}

fun Int.swipeDown() {
    getFirstViewById(this).perform(ViewActions.swipeDown())
}

fun Int.swipeUp() {
    getFirstViewById(this).perform(ViewActions.swipeUp())
}

fun waitFor(delay: Long) = onView(isRoot()).perform(expressoDelay(delay))

private fun expressoDelay(delay: Long): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription(): String = "wait for $delay milliseconds"
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}