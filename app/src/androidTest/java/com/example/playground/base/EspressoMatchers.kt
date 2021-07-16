package com.example.playground.base

import android.view.View
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object EspressoMatchers {

    fun withText(text: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            public override fun matchesSafely(view: View): Boolean {
                return when (view) {
                    is TextView -> view.text.toString() == text
                    else -> false
                }
            }

            override fun describeTo(description: Description) {
                description.appendText("text should be: $text")
            }
        }
    }

    fun withHint(hint: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            public override fun matchesSafely(view: View): Boolean {
                return when (view) {
                    is TextView -> view.hint.toString() == hint
                    else -> false
                }
            }

            override fun describeTo(description: Description) {
                description.appendText("hint should be: $hint")
            }
        }
    }
}
