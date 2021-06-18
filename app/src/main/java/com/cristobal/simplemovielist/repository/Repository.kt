package com.cristobal.simplemovielist.repository

class Repository(private val retrofitService: RetrofitService) {
	suspend fun getDiscoverFilms(language: String, page: Int) = retrofitService.getDiscoverFilms(language, page)
}