package com.cristobal.simplemovielist.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentSplashBinding
import com.cristobal.simplemovielist.repository.LoadState
import kotlinx.coroutines.flow.collect

// This fragment is only showed at the start, while the first webservice call
class SplashFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel>()

	private var _binding: FragmentSplashBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentSplashBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		(activity as AppCompatActivity?)!!.supportActionBar!!.hide()
	}

	init {
		lifecycleScope.launchWhenStarted {
			viewModel.films.collect {
				when (it) {
					is LoadState.Unstarted -> {}

					is LoadState.Error -> {
						binding.connectionErrorLayout.visibility = View.VISIBLE
						binding.reconnectButton.setOnClickListener {
							viewModel.loadFilms()
						}
					}

					is LoadState.Loading -> {
						binding.connectionErrorLayout.visibility = View.INVISIBLE
					}

					is LoadState.Success -> {
						val navController = NavHostFragment.findNavController(this@SplashFragment)
						navController.navigate(R.id.action_splashFragment_to_movieListFragment)
					}
				}}
			}
		}
}