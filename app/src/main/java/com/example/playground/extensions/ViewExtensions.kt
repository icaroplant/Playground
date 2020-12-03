package com.example.playground.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Extension method to hide a keyboard from a view.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun View.changeVisibility(isVisible: Boolean) {
    visibility = takeIf { isVisible }?.run { View.VISIBLE } ?: View.GONE
}
