package com.cristobal.simplemovielist.model

// Whole data of one single film
data class Film(
	val id: Int,
	val backdrop_path: String,
    val poster_path: String,
	val title: String,
	val release_date: String,
	val original_title: String,
	val overview: String,
	val vote_average: String
	)

