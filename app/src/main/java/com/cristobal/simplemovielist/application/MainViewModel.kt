package com.cristobal.simplemovielist.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.cristobal.simplemovielist.model.Film
import com.cristobal.simplemovielist.repository.FilmsPagingSource
import com.cristobal.simplemovielist.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

	private val IMAGE_START_URI = "https://image.tmdb.org/t/p/w500"

	private val _appBarTitle = MutableStateFlow<String>("")
	val appBarTitle = _appBarTitle.asStateFlow()

	private val _goToMainList = MutableSharedFlow<Boolean>()
	val goToMainList = _goToMainList.asSharedFlow()

	private val _firstLoadError = MutableSharedFlow<Boolean>()
	val firstLoadError = _firstLoadError.asSharedFlow()


	// When there is a new film to be shown in detail, the event will trigger the navigation to the detail screen
	private val _newDetailFilm = MutableStateFlow<Film?>(null)
	val newDetailFilm = _newDetailFilm.asStateFlow()

	fun goToMainList() {
		viewModelScope.launch {
			_goToMainList.emit(true)
		}
	}

	fun firstLoadError() {
		viewModelScope.launch {
			_firstLoadError.emit(true)
		}
	}

	/*	fun loadFilms() {
			_films.value = LoadState.Loading
			viewModelScope.launch {
				try {
					val deviceLanguage = Locale.getDefault().language
					_films.value = LoadState.Success(repository.getDiscoverFilms(deviceLanguage, actualPage).results)
				} catch (e: Exception) {
					_films.value = LoadState.Error(e.toString())
				}
			}
		}*/

	val films: Flow<PagingData<Film>> =
		Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 5),
			  pagingSourceFactory = { FilmsPagingSource(repository.retrofitService) }
			 ).flow.cachedIn(viewModelScope)


	// Builds final uri for the image partial path
	fun getImageFullUri(image: String): String {
		return IMAGE_START_URI + image
	}

	fun openDetailFilm(film: Film) {
		viewModelScope.launch {
			_newDetailFilm.value = film
		}
	}

	fun resetDetailFilm() {
		viewModelScope.launch {
			_newDetailFilm.value = null
		}
	}

	fun setActionBarTitle(title: String) {
		_appBarTitle.value = title
	}
}