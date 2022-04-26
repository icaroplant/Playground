package com.example.playground.ui.manage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicModel (
    var id: Long,
    var name: String,
    var artist: String? = null,
    var track: String? = null
) : Parcelable