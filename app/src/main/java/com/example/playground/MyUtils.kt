package com.example.playground

import android.widget.Toast
import androidx.annotation.NonNull
import java.lang.Exception

fun makeToastAndShow(@NonNull msg: String){
    try {
        Toast.makeText(MyApp.context, msg, Toast.LENGTH_SHORT).show()
    } catch (e: Exception){
    }
}