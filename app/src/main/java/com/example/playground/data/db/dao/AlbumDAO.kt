package com.example.playground.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.playground.data.db.entity.AlbumEntity

@Dao
interface AlbumDAO {

    @Insert
    suspend fun insert(AlbumEntity: AlbumEntity) : Long

    @Update
    suspend fun update(AlbumEntity: AlbumEntity)

    @Query("DELETE FROM Album WHERE id = :id")
    suspend fun delete(id: String): Int

    @Query("DELETE FROM Album")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM Album")
    suspend fun getAll() : List<AlbumEntity>
}