package com.cristobal.simplemovielist.ui.movieDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentMovieDetailBinding
import com.cristobal.simplemovielist.databinding.FragmentMovieListBinding
import com.cristobal.simplemovielist.ui.splash.SplashFragment

class MovieDetailFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel>()

	private var _binding: FragmentMovieDetailBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
		return binding.root
	}


}