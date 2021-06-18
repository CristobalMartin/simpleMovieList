package com.cristobal.simplemovielist.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.databinding.ActivityMainBinding
import com.cristobal.simplemovielist.ui.movieList.MovieListFragment
import com.cristobal.simplemovielist.ui.splash.SplashFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	val viewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)
	}

	init {
		lifecycleScope.launchWhenStarted {
			viewModel.newDetailFilm.collect {
				if (it != null) {
					val navController = findNavController(R.id.fragmentContainerView)
					navController.navigate(R.id.action_movieListFragment_to_movieDetailFragment)
				}
			}
		}
	}
}