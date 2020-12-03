package com.example.playground.ui.manage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicModel (
    var id: Long,
    var name: String,
    var artist: String?,
    var track: String?
) : Parcelable