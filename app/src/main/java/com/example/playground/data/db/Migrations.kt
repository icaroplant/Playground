package com.example.playground.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS `Music`")
        database.execSQL("CREATE TABLE IF NOT EXISTS `Music` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `artist` TEXT, `track` INTEGER, `albumId` INTEGER)")
    }
}