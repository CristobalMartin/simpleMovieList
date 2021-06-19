package com.cristobal.simplemovielist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Whole data of one single film
// This very same model is used for Retrofit and Room
@Entity
data class Film(
	@PrimaryKey(autoGenerate = true)
	var uid: Long = 0,
	val id: Int,
	val backdrop_path: String,
    val poster_path: String,
	val title: String,
	val release_date: String,
	val original_title: String,
	val overview: String,
	val vote_average: String
	)  : Serializable

