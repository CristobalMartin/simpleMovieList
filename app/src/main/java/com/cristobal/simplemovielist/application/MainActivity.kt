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
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	val viewModel: MainViewModel by viewModels()

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var navController: NavController


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
		navController = navHostFragment.navController
		appBarConfiguration = AppBarConfiguration(navController.graph)
		setupActionBarWithNavController(navController, appBarConfiguration)

	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp(appBarConfiguration)
				|| super.onSupportNavigateUp()
	}

	// This is because the up button must behave like back button. If not, the splash screen restarts
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
		lifecycleScope.launchWhenStarted {
			viewModel.newDetailFilm.collect {
				if (it != null) {
					navController.navigate(R.id.action_movieListFragment_to_movieDetailFragment)
				}
			}
		}

		lifecycleScope.launchWhenStarted {
			viewModel.appBarTitle.collect {
				supportActionBar?.title = it
			}
		}
	}
}