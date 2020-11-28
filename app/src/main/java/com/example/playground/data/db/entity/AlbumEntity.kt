package com.example.playground.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Album")
data class AlbumEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,

    val name: String,
    val year: String? = null,

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: String
)