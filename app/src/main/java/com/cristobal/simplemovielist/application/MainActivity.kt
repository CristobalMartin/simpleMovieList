package com.cristobal.simplemovielist.application

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.paging.LoadState
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.databinding.ActivityMainBinding
import com.cristobal.simplemovielist.ui.movieList.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

// Uses the single activity architecture
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	val viewModel: MainViewModel by viewModels()

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var navController: NavController

	lateinit var movieListAdapter: MovieListAdapter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
		navController = navHostFragment.navController
		appBarConfiguration = AppBarConfiguration(navController.graph)
		setupActionBarWithNavController(navController, appBarConfiguration)

		movieListAdapter = MovieListAdapter(this, viewModel)

		// With paging 3, the listener of the state of the load is in the adapter
		// We need the listener in two fragments, so the adapter is in the main activity, and therefore the webservice load starts as soon as the app is launched.
		// The splash fragment receives the state of the first load, and the main list fragment uses the adapter as it is
		movieListAdapter.addLoadStateListener { loadState ->

			//In this situation, the first load is successfully
			if (loadState.prepend.endOfPaginationReached) {
				viewModel.goToMainList()
			}

			if (loadState.refresh is LoadState.Error) {
				viewModel.firstLoadError()
			}
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	// This is because the up button must behave like the back button. If not, the splash screen restarts
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> {
				onBackPressed()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}

	init {

		// The next observers are for navigating among screens.
		lifecycleScope.launchWhenStarted {
			viewModel.newDetailFilmFromMainList.collect {
				if (it) {
					navController.navigate(R.id.action_movieListFragment_to_movieDetailFragment)
				}
			}
		}

		lifecycleScope.launchWhenStarted {
			viewModel.newDetailFilmFromFavorites.collect {
				if (it) {
					navController.navigate(R.id.action_favoritesListFragment_to_movieDetailFragment)
				}
			}
		}

		lifecycleScope.launchWhenStarted {
			viewModel.goToFavoritesList.collect {
				if (it) {
					navController.navigate(R.id.action_movieListFragment_to_favoritesListFragment)
				}
			}
		}

		// Changes the app bar title
		lifecycleScope.launchWhenStarted {
			viewModel.appBarTitle.collect {
				supportActionBar?.title = it
			}
		}

		// Required for the paging library
		lifecycleScope.launchWhenStarted {
			viewModel.films.collectLatest { pagingData ->
				movieListAdapter.submitData(pagingData)
			}
		}
	}
}