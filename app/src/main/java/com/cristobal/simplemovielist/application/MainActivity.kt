package com.cristobal.simplemovielist.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cristobal.simplemovielist.R
import com.cristobal.simplemovielist.databinding.ActivityMainBinding
import com.cristobal.simplemovielist.ui.movieList.MovieListFragment
import com.cristobal.simplemovielist.ui.splash.SplashFragment

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

	}
}