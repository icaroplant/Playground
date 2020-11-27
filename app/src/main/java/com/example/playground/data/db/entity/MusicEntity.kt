package com.example.playground.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Music")
data class MusicEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String?,
    val artist: String?,
    val album: String?,
    val track: String?
)