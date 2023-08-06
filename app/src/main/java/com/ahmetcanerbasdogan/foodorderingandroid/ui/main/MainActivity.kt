package com.ahmetcanerbasdogan.foodorderingandroid.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ahmetcanerbasdogan.foodorderingandroid.R
import com.ahmetcanerbasdogan.foodorderingandroid.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tempViewModel: MainViewModel by viewModels()
        viewModel = tempViewModel

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val currentDestinationId = navController.currentDestination?.id
            val selectedDestinationId = menuItem.itemId

            if (currentDestinationId != selectedDestinationId) {
                navController.navigate(selectedDestinationId)
            }
            true
        }
    }
}