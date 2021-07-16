package com.example.playground.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

inline fun <reified T> MutableLiveData<T>.asImmutable(): LiveData<T> = this