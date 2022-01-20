package com.example.playground.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openDeepLink(link: String) {
    Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(link)
    }.also {
        startActivity(it)
    }
}