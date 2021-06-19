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
import com.cristobal.simplemovielist.application.MainActivity
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.goToMainList.collect {
				val navController = NavHostFragment.findNavController(this@SplashFragment)
				navController.navigate(R.id.action_splashFragment_to_movieListFragment)
			}
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.firstLoadError.collect {
				binding.connectionErrorLayout.visibility = View.VISIBLE
				binding.reconnectButton.setOnClickListener {
					binding.connectionErrorLayout.visibility = View.INVISIBLE
					(activity as MainActivity).movieListAdapter.retry()
				}
			}
		}
	}
}