package com.example.playground.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

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

fun Fragment.makeSnackBar(@NonNull msg: String){
    try {
        view?.run {
            Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
        }
    } catch (e: Exception){
    }
}

fun Fragment.makeSnackBarWithAction(
    @NonNull msg: String,
    @NonNull actionText: String,
    @NonNull actionListener: (View) -> Unit
){
    try {
        view?.run {
            Snackbar.make(this, msg, Snackbar.LENGTH_LONG).setAction(actionText, actionListener).show()
        }
    } catch (e: Exception){
    }
}

fun Fragment.makeSnackBarFrom(view: View?, @NonNull msg: String){
    try {
        view?.run {
            Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
        }
    } catch (e: Exception){
    }
}

fun Fragment.makeSnackBarWithActionFrom(
    view: View?,
    @NonNull msg: String,
    @NonNull actionText: String,
    @NonNull actionListener: (View) -> Unit
){
    try {
        view?.run {
            Snackbar.make(this, msg, Snackbar.LENGTH_LONG).setAction(actionText, actionListener).show()
        }
    } catch (e: Exception){
    }
}

fun Fragment.forceHideKeyboard() {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Fragment.handleOnBackPressed(callback: OnBackPressedCallback.() -> Unit) {
    activity?.onBackPressedDispatcher?.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callback()
            }
        }
    )
}