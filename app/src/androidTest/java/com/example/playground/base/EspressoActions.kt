package com.example.playground.base

import android.view.View
import android.widget.EditText
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

object EspressoActions {

    fun applyText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "set Error"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(EditText::class.java)
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as EditText).setText(text)
            }
        }
    }
}
