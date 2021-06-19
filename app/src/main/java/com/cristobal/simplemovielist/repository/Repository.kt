package com.cristobal.simplemovielist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cristobal.simplemovielist.model.Film
import com.cristobal.simplemovielist.repository.room.FilmDao
import kotlinx.coroutines.flow.Flow

const val PAGE_SIZE = 20

class Repository(private val retrofitService: RetrofitService, private val filmDao: FilmDao) {

	//	suspend fun getDiscoverFilms(language: String, page: Int) = retrofitService.getDiscoverFilms(language, page)

	fun getDiscoverFilms(): Flow<PagingData<Film>> = Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 5), pagingSourceFactory = { FilmsPagingSource(retrofitService) }).flow


	fun insertFavorite(film: Film) {
		filmDao.insert(film)
	}

	fun deleteFavorite(film: Film) {
		filmDao.deleteById(film.id)
	}

	fun inFavorites(film: Film) :Boolean {
		return (filmDao.getFilm(film.id) != null)
	}

	fun getFavorites(): MutableList<Film> {
		return filmDao.getFilms()
	}

}