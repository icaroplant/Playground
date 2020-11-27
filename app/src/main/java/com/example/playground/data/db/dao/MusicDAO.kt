package com.example.playground.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.playground.data.db.entity.MusicEntity

@Dao
interface  MusicDAO {

    @Insert
    suspend fun insert(musicEntity: MusicEntity) : Long

    @Update
    suspend fun update(musicEntity: MusicEntity)

    @Query("DELETE FROM Music WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM Music")
    suspend fun deleteAll()

    @Query("SELECT * FROM Music")
    suspend fun getAll()

    @Query("SELECT * FROM Music WHERE name = :name ORDER BY name, artist, album, track")
    suspend fun getByName(name: String) : LiveData<List<MusicEntity>>

}