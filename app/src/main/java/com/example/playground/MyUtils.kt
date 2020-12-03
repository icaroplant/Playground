package com.example.playground

import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import java.lang.Exception

fun makeToast(@NonNull msg: String){
    try {
        Toast.makeText(MyApp.context, msg, Toast.LENGTH_SHORT).show()
    } catch (e: Exception){
    }
}

fun getColor(@ColorRes colorId: Int) : Int{
    return ContextCompat.getColor(MyApp.context, colorId)
}