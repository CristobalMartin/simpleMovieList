package com.cristobal.simplemovielist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cristobal.simplemovielist.model.Film
import kotlinx.coroutines.flow.Flow

const val PAGE_SIZE = 20

class Repository(val retrofitService: RetrofitService) {

	//	suspend fun getDiscoverFilms(language: String, page: Int) = retrofitService.getDiscoverFilms(language, page)

	fun getDiscoverFilms(): Flow<PagingData<Film>> {
		return Pager(config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false), pagingSourceFactory = {
			FilmsPagingSource(service = retrofitService)
		}).flow
	}
}