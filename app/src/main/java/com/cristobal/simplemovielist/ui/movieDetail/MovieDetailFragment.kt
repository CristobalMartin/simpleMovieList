package com.cristobal.simplemovielist.ui.movieDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentMovieDetailBinding
import com.cristobal.simplemovielist.model.Film
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel>()

	private var _binding: FragmentMovieDetailBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.newDetailFilm.collect {

				// Here, newDetailFilm must be not null
				if (it != null) {
					showDetailedData(it)
				}
			}
		}
	}

	fun showDetailedData(film: Film) {
		viewModel.setActionBarTitle(film.title)

		binding.titleTextView.text = film.title
		if (film.original_title != film.title) {
			binding.originalTitleTextView.text = getString(R.string.original_title, film.original_title)
		}

		binding.rateTextView.text = film.vote_average

		binding.overViewTextView.text = film.overview

		Glide.with(this@MovieDetailFragment)
				.load(viewModel.getImageFullUri(film.poster_path))
				.placeholder(R.drawable.poster_placeholder)
				.into(binding.posterImage)

		Glide.with(this@MovieDetailFragment)
				.load(viewModel.getImageFullUri(film.backdrop_path))
				.placeholder(R.drawable.poster_placeholder)
				.into(binding.bannerImage)
	}
}