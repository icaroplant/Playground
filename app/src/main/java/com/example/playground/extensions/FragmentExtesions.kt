package com.example.playground.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 *
 * This extension should be used for avoiding boilerplate when observing a LiveData
 * <pre>
 *
 *     Remove this:
 *
 *     val liveData = MutableLiveData<Foo>()
 *     liveData.observe(lifecycleOwner, Observer { print(it) })
 *
 *     And use this:
 *
 *     val liveData = MutableLiveData<Foo>()
 *     observe(liveData) { print(it) }
 *
 * </pre>
 */
inline fun <reified T> Fragment.observe(
    liveData: LiveData<T>,
    crossinline execution: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, Observer { execution(it) })
}