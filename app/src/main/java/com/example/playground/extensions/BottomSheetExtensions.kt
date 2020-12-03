package com.example.playground.extensions

import android.content.res.Resources
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.Px
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.adjustResize() {
    dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

fun BottomSheetDialogFragment.setStateExpanded() {
    dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)?.run {
        BottomSheetBehavior.from(this).state = BottomSheetBehavior.STATE_EXPANDED
    }
}

fun BottomSheetDialogFragment.setExpandedOffset(@Px offset: Int) {
    dialog?.setOnShowListener {
        dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)?.run {
            BottomSheetBehavior.from(this).run {
                isFitToContents = false
                setExpandedOffset(offset)
            }
        }
    }
}

fun BottomSheetDialogFragment.setFitToContents(fitToContents: Boolean) {
    dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)?.let {
        BottomSheetBehavior.from(it).isFitToContents = fitToContents
    }
}

/**
 * Resize the bottom sheet to have the screen size and change style to have marginTop
 * */
fun BottomSheetDialogFragment.maximized(useTouchOutside: Boolean = false) {
    dialog?.setOnShowListener {
        dialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            ?.apply {
                val heightPixels = Resources.getSystem().displayMetrics.heightPixels
                val height = if (useTouchOutside)
                    dialog?.findViewById<View>(com.google.android.material.R.id.touch_outside)?.height
                        ?: heightPixels
                else
                    heightPixels
                layoutParams.height = height
            }
    }
}

fun BottomSheetDialogFragment.adjustPan() {
    dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}