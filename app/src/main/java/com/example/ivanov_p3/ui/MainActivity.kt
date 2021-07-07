package com.example.ivanov_p3.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.ivanov_p3.R
import com.example.ivanov_p3.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigation.visibility = View.INVISIBLE

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.splashFragment) {
                binding.bottomNavigation.visibility = View.INVISIBLE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}