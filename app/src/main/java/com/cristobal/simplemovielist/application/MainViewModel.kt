package com.cristobal.simplemovielist.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.cristobal.simplemovielist.model.Film
import com.cristobal.simplemovielist.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

	private val IMAGE_START_URI = "https://image.tmdb.org/t/p/w500"

	private val _appBarTitle = MutableStateFlow("")
	val appBarTitle = _appBarTitle.asStateFlow()


	private val _firstLoadError = MutableSharedFlow<Boolean>()
	val firstLoadError = _firstLoadError.asSharedFlow()

	private val _favoriteFilmsAmount = MutableStateFlow(0)
	val favoriteFilmsAmount = _favoriteFilmsAmount.asSharedFlow()

	// Go from splash to main list
	private val _goToMainList = MutableSharedFlow<Boolean>()
	val goToMainList = _goToMainList.asSharedFlow()

	// Go from main list to favorites
	private val _goToFavoritesList = MutableSharedFlow<Boolean>()
	val goToFavoritesList = _goToFavoritesList.asSharedFlow()

	// When there is a new film to be shown in detail, the event will trigger the navigation to the detail screen
	// Can be done from main list and from favorites
	// We need to different variables because it triggers two different navegation actions
	private val _newDetailFilmFromMainList = MutableSharedFlow<Boolean>()
	val newDetailFilmFromMainList = _newDetailFilmFromMainList.asSharedFlow()

	private val _newDetailFilmFromFavorites = MutableSharedFlow<Boolean>()
	val newDetailFilmFromFavorites = _newDetailFilmFromFavorites.asSharedFlow()

	private val _newDetailFilm = MutableStateFlow<Film?>(null)
	val newDetailFilm = _newDetailFilm.asStateFlow()

	init {
		viewModelScope.launch(Dispatchers.IO) {
			_favoriteFilmsAmount.value = repository.getFavorites().size
		}
	}

	fun goToMainList() {
		viewModelScope.launch {
			_goToMainList.emit(true)
		}
	}

	fun goToFavoritesList() {
		viewModelScope.launch {
			_goToFavoritesList.emit(true)
		}
	}

	fun firstLoadError() {
		viewModelScope.launch {
			_firstLoadError.emit(true)
		}
	}

	// Paging data for the infinite scroll in the film list
	val films = repository.getDiscoverFilms().cachedIn(viewModelScope)


	// Builds final uri for the image partial path
	fun getImageFullUri(image: String): String {
		return IMAGE_START_URI + image
	}

	fun openDetailFilmFromMainList(film: Film) {
		viewModelScope.launch {
			_newDetailFilmFromMainList.emit(true)
			_newDetailFilm.value = film
		}
	}

	fun openDetailFilmFromFavorites(film: Film) {
		viewModelScope.launch {
			_newDetailFilmFromFavorites.emit(true)
			_newDetailFilm.value = film
		}
	}

	fun setActionBarTitle(title: String) {
		_appBarTitle.value = title
	}


	// Functions for favorites in local database

	suspend fun addFilmToFavorites(film: Film) {
		withContext((Dispatchers.IO)) {
			repository.insertFavorite(film)
			_favoriteFilmsAmount.value++
		}
	}

	suspend fun removeFilmFromFavorites(film: Film) {
		withContext((Dispatchers.IO)) {
			repository.deleteFavorite(film)
			_favoriteFilmsAmount.value--
		}
	}

	suspend fun checkFilmInFavorites(film: Film): Boolean =
		withContext((Dispatchers.IO)) {
			return@withContext (repository.inFavorites(film))
		}

	suspend fun getFavoriteFilms(): MutableList<Film> =
		withContext((Dispatchers.IO)) {
			return@withContext repository.getFavorites()
		}

}