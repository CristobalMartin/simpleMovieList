package com.cristobal.simplemovielist.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cristobal.simplemovielist.model.Film

@Database(entities = [Film::class],
          version = 1, exportSchema = false)

abstract class RoomDataBase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}