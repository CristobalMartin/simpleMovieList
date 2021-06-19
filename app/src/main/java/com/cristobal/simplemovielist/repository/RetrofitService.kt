package com.cristobal.simplemovielist.repository

import com.cristobal.simplemovielist.model.DiscoverFilmsWSResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

	// Webservice for new films
	@GET("discover/movie?api_key=b11e286d034b87557a46a191224cd910&sort_by=popularity.desc&include_adult=false&include_video=false")
	suspend fun getDiscoverFilms(@Query("language") language: String,@Query("page") page: Int): DiscoverFilmsWSResult
}
