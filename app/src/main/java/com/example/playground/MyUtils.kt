package com.example.playground

import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

fun makeToast(@NonNull msg: String){
    try {
        Toast.makeText(MyApp.context, msg, Toast.LENGTH_SHORT).show()
    } catch (e: Exception){
    }
}