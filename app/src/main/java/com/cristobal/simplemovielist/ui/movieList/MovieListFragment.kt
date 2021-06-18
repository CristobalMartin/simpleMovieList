package com.cristobal.simplemovielist.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentMovieListBinding
import com.cristobal.simplemovielist.repository.LoadState
import kotlinx.coroutines.flow.collect

class MovieListFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel>()

	private var _binding: FragmentMovieListBinding? = null
	private val binding get() = _binding!!

	private lateinit var movieListAdapter: MovieListAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		(activity as AppCompatActivity?)!!.supportActionBar!!.show()
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentMovieListBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		movieListAdapter = MovieListAdapter(mutableListOf(), this@MovieListFragment.requireContext(), viewModel)

		binding.recyclerView.adapter = movieListAdapter
	}

	init {
		lifecycleScope.launchWhenStarted {
			viewModel.films.collect {

				// The only state available here
				if (it is LoadState.Success) {
					movieListAdapter.setItems(it.data)
				}
			}
		}
	}


}