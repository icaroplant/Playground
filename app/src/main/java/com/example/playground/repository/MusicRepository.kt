package com.example.playground.repository

import androidx.lifecycle.LiveData
import com.example.playground.data.db.entity.MusicEntity

interface MusicRepository {

    suspend fun insertMusic(
        name: String,
        artist: String? = null,
        track: Int? = null,
        albumName: String? = null
    ) : Long

    suspend fun updateMusic(
        id: Long,
        name: String,
        artist: String? = null,
        track: Int? = null,
        albumName: String? = null
    )

    suspend fun deleteMusic(id: Long)

    suspend fun deleteAllMusics()

    suspend fun getMusic(id: Long) : List<MusicEntity>

    suspend fun getMusicsByArtist(artist: String) : List<MusicEntity>

    suspend fun getAllMusics() : List<MusicEntity>
}