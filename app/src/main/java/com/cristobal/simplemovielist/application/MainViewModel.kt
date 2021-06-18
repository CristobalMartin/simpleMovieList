package com.cristobal.simplemovielist.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristobal.simplemovielist.model.Film
import com.cristobal.simplemovielist.repository.LoadState
import com.cristobal.simplemovielist.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

	private val IMAGE_START_URI = "https://image.tmdb.org/t/p/w500"

	private val _appBarTitle = MutableStateFlow<String>("")
	val appBarTitle = _appBarTitle.asStateFlow()

	private val _films = MutableStateFlow<LoadState<List<Film>>>(LoadState.Unstarted)
	val films = _films.asStateFlow()

	// When there is a new film to be shown in detail, the event will trigger the navigation to the detail screen
	private val _newDetailFilm = MutableStateFlow<Film?>(null)
	val newDetailFilm = _newDetailFilm.asStateFlow()

	// For pagination of the films query
	var actualPage = 1

	init {
		loadFilms()
	}

	fun loadFilms() {
		_films.value = LoadState.Loading
		viewModelScope.launch {
			try {
				val deviceLanguage = Locale.getDefault().language
				_films.value = LoadState.Success(repository.getDiscoverFilms(deviceLanguage, actualPage).results)
			} catch (e: Exception) {
				_films.value = LoadState.Error(e.toString())
			}
		}
	}

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