package com.cristobal.simplemovielist.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cristobal.simplemovielist.model.Film

// CRUD for films in local database
@Dao
interface FilmDao {
    @Query("SELECT * FROM Film ORDER BY release_date DESC")
    fun getFilms(): MutableList<Film>

    @Query("SELECT * FROM Film WHERE id=:id")
    fun getFilm(id: Int): Film?

    @Update
    fun update(objects: Film): Int

    @Insert
    fun insert(objects: Film): Long

    @Query("DELETE FROM Film WHERE id = :id")
    fun deleteById(id: Int)
}
