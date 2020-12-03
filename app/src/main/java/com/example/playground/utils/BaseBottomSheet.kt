package com.example.playground.utils

import android.content.DialogInterface
import com.example.playground.R
import com.example.playground.extensions.adjustResize
import com.example.playground.extensions.hideKeyboard
import com.example.playground.extensions.setStateExpanded
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias ButtonAction = () -> Unit

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.BottomSheetDialog

    override fun onStart() {
        super.onStart()

        adjustResize()
        setStateExpanded()
    }

    override fun onDismiss(dialog: DialogInterface) {
        view?.hideKeyboard()

        super.onDismiss(dialog)
    }
}