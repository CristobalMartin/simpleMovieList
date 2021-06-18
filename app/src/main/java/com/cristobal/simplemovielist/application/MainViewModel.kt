package com.cristobal.simplemovielist.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristobal.simplemovielist.model.Film
import com.cristobal.simplemovielist.repository.LoadState
import com.cristobal.simplemovielist.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

	private val _films = MutableStateFlow<LoadState<List<Film>>>(LoadState.Unstarted)
	val films = _films.asStateFlow()

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
}