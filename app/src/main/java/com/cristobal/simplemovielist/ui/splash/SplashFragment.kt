package com.cristobal.simplemovielist.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cristobal.simplemovielist.application.MainViewModel
import com.cristobal.simplemovielist.databinding.FragmentSplashBinding

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

}