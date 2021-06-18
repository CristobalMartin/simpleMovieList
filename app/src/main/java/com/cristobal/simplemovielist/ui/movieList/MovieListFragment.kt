package com.cristobal.simplemovielist.ui.movieList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel>()

	private var _binding: FragmentMovieListBinding? = null
	private val binding get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		(activity as AppCompatActivity?)!!.supportActionBar!!.show()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentMovieListBinding.inflate(inflater, container, false)
		return binding.root
	}

}