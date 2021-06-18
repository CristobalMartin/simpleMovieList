package com.cristobal.simplemovielist.ui.movieDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentMovieDetailBinding
import com.cristobal.simplemovielist.databinding.FragmentMovieListBinding
import com.cristobal.simplemovielist.ui.splash.SplashFragment
import kotlinx.coroutines.flow.collect

class MovieDetailFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel>()

	private var _binding: FragmentMovieDetailBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
		return binding.root
	}

	init {
		lifecycleScope.launchWhenStarted {
			viewModel.newDetailFilm.collect {

				// Here, newDetailFilm must be not null
				if (it != null) {

					viewModel.setActionBarTitle(it.title)

					binding.titleTextView.text = it.title
					if (it.original_title != it.title) {
						binding.originalTitleTextView.text = getString(R.string.original_title, it.original_title)
					}

					binding.rateTextView.text = it.vote_average

					binding.overViewTextView.text = it.overview

					Glide.with(this@MovieDetailFragment)
							.load(viewModel.getImageFullUri(it.poster_path))
							.placeholder(R.drawable.poster_placeholder)
							.into(binding.posterImage)

					Glide.with(this@MovieDetailFragment)
							.load(viewModel.getImageFullUri(it.backdrop_path))
							.placeholder(R.drawable.poster_placeholder)
							.into(binding.bannerImage)

					// When the data is set, newDetailFilm must be reseted
					// If not, if the next film is the same that the previous, the detal wont open
					viewModel.resetDetailFilm()
				}

			}
		}
	}
}