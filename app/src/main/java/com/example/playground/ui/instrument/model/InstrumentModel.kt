package com.example.playground.ui.instrument.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstrumentModel(
    val name: String,
    val type: Type,
    val resId: Int
) : Parcelable {
    enum class Type {
        MIC,
        GUITAR,
        BASS,
        DRUMS
    }
}

