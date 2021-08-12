package com.example.playground.utils

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import com.example.playground.R
import kotlinx.android.synthetic.main.dialog_image_viewer.*

class DialogImageViewer(
    val context: Context,
    val resId: Int
) {

    private val dialog: Dialog = Dialog(context).apply {
        setContentView(R.layout.dialog_image_viewer)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        pvDialogImagePhoto.setImageResource(resId)
        ivDialogImageClose.setOnClickListener {
            dismiss()
        }
    }

    fun show() {
        dialog.show()
    }

}