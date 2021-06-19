package com.cristobal.simplemovielist.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.cristobal.simplemovielist.model.Film
import java.io.IOException
import java.util.*

private const val START_INDEX = 1

class FilmsPagingSource(private val service: RetrofitService) : PagingSource<Int, Film>() {

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
		val pageIndex = params.key ?: START_INDEX
		return try {
			val deviceLanguage = Locale.getDefault().language
			val response = service.getDiscoverFilms(deviceLanguage, pageIndex)
			val films = response.results

			val nextKey = if (films.isEmpty()) {
				null
			} else {
				pageIndex + 1
			}
			val prevKey = if (pageIndex == START_INDEX) {
				null
			} else {
				pageIndex
			}

			LoadResult.Page(data = films, prevKey, nextKey = nextKey)

		} catch (exception: IOException) {
			return LoadResult.Error(exception)
		} catch (exception: HttpException) {
			return LoadResult.Error(exception)
		}
	}

	/**
	 * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
	 */
	override fun getRefreshKey(state: PagingState<Int, Film>): Int? {        // We need to get the previous key (or next key if previous is null) of the page
		// that was closest to the most recently accessed index.
		// Anchor position is the most recently accessed index.
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}
}