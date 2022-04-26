package com.example.playground.utils

import android.app.Activity
import android.app.Dialog
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.playground.R
import com.example.playground.databinding.DialogImageViewerBinding

class DialogImageViewer(
    private val activity: Activity,
    private val resId: Int
) {

    private val dialog: Dialog = Dialog(activity).apply {
        val binding: DialogImageViewerBinding = DataBindingUtil.setContentView(activity, R.layout.dialog_image_viewer)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        binding.pvDialogImagePhoto.setImageResource(resId)
        binding.ivDialogImageClose.setOnClickListener {
            dismiss()
        }
    }

    fun show() {
        dialog.show()
    }

}