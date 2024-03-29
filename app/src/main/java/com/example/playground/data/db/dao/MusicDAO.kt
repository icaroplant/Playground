package com.example.playground.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.playground.data.db.entity.MusicEntity

@Dao
interface MusicDAO {

    @Insert
    suspend fun insert(musicEntity: MusicEntity): Long

    @Update
    suspend fun update(musicEntity: MusicEntity)

    @Query("DELETE FROM Music WHERE id = :id")
    suspend fun delete(id: Long): Int

    @Query("DELETE FROM Music")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM Music")
    fun getAll(): LiveData<List<MusicEntity>>

    @Query("SELECT * FROM Music WHERE id = :id")
    suspend fun getById(id: Long): List<MusicEntity>

    @Query("SELECT * FROM Music WHERE artist = :artist ORDER BY name, artist, track")
    suspend fun getByArtist(artist: String): List<MusicEntity>

}