package com.example.playground.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Music",
    foreignKeys = [
        ForeignKey(
            entity = AlbumEntity::class,
            parentColumns = ["id"],
            childColumns = ["albumId"],
            onDelete = ForeignKey.CASCADE)
        ]
)
data class MusicEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String? = null,
    val artist: String? = null,
    val track: Int? = null,
    val albumId : Long? = null,

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: String? = null
)