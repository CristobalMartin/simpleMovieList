package com.cristobal.simplemovielist.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainActivity
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentMovieListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.recyclerView.adapter = (activity as MainActivity).movieListAdapter  // little wierd, but an adapter can not be in the viewmodel
		viewModel.setActionBarTitle(getString(R.string.filmListBarTitle))

		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.favoriteFilmsAmount.collect {
				if (it > 0) {
					binding.favoritesActionButton.visibility = View.VISIBLE
				} else {
					binding.favoritesActionButton.visibility = View.INVISIBLE
				}
			}
		}

		binding.favoritesActionButton.setOnClickListener {
			viewModel.goToFavoritesList()
		}
	}
}