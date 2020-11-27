package com.example.playground.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.example.playground.R

private val navOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_from_right)
    .setExitAnim(R.anim.slide_out_to_left)
    .setPopEnterAnim(R.anim.slide_in_from_left)
    .setPopExitAnim(R.anim.slide_out_to_right)
    .build()

fun NavController.navigateWithAnimations(@IdRes resId : Int){
    this.navigate(resId, null, navOptions)
}

fun NavController.navigateWithAnimations(@IdRes resId : Int, bundle: Bundle){
    this.navigate(resId, bundle, navOptions)
}
fun NavController.navigateWithAnimations(@NonNull directions: NavDirections){
    this.navigate(directions, navOptions)
}