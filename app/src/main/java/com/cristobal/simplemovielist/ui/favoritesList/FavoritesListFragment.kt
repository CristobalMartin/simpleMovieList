package com.cristobal.simplemovielist.ui.favoritesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentMovieListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesListFragment : Fragment() {

	private val viewModel by activityViewModels<MainViewModel>()

	private var _binding: FragmentMovieListBinding? = null
	private val binding get() = _binding!!

	private lateinit var favoritesListAdapter: FavoritesListAdapter


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

		favoritesListAdapter = FavoritesListAdapter(mutableListOf(), this@FavoritesListFragment.requireContext(), viewModel)

		binding.recyclerView.adapter = favoritesListAdapter
		viewModel.setActionBarTitle(getString(R.string.favoritesListBarTitle))
		binding.favoritesActionButton.visibility = View.INVISIBLE

		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.favoriteFilmsAmount.collect {
				if (it < 1) {
					activity?.onBackPressed()
				}
			}
		}

		// Reload the favorites list every time this fragment is created
		viewLifecycleOwner.lifecycleScope.launch {
			favoritesListAdapter.setItems(viewModel.getFavoriteFilms())
		}
	}
}