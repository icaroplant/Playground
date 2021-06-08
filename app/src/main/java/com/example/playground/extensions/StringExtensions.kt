package com.example.playground.extensions

import android.text.Spanned
import androidx.core.text.HtmlCompat

inline val String.toHtml: Spanned get() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT)