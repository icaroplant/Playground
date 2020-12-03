package com.example.playground.extensions

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.showOnce(fragmentManager: FragmentManager) {
    try {
        val tag = this::class.java.simpleName

        if (fragmentManager.findFragmentByTag(tag) == null) {
            show(fragmentManager, tag)
        }
    } catch (error: Throwable) {
    }

}