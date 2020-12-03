package com.example.playground.utils

import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.example.playground.MyApp
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

data class LiveDataSingleEvent<out T>(private val content: T) {

    private var hasBeenHandled = false

    val contentIfNotHandled: T?
        get() = if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }

    fun peekContent(): T = content
}