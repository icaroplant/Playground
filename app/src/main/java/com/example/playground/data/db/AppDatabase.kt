package com.example.playground.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.playground.data.db.dao.AlbumDAO
import com.example.playground.data.db.dao.MusicDAO
import com.example.playground.data.db.entity.AlbumEntity
import com.example.playground.data.db.entity.MusicEntity

@Database(entities = [
    MusicEntity::class,
    AlbumEntity::class
], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val musicDAO : MusicDAO
    abstract val albumDAO : AlbumDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .addMigrations()
                        .build()
                }

                return instance
            }
        }
    }

}