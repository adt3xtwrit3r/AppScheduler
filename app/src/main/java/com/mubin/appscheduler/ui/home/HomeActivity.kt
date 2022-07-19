package com.mubin.appscheduler.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mubin.appscheduler.R
import com.mubin.appscheduler.databinding.ActivityMainBinding
import com.mubin.appscheduler.ui.HomeCommunicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeCommunicator {

    private var binding: ActivityMainBinding? = null

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initNavGraph()
        initToolbar()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) {
            return
        }
    }

    private fun initNavGraph() {
        navController = findNavController(R.id.navHostFragment)

        if (navController.currentDestination?.id != navController.graph.startDestination) {
            binding?.toolbar?.visibility = View.VISIBLE
        } else {
            binding?.toolbar?.visibility = View.GONE
        }
    } // initializing Navigation Controller

    private fun initToolbar() {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding?.toolbar?.setupWithNavController(navController, appBarConfiguration)
    } // initializing toolbar with Navigation Controller

    override fun initToolbarToggle() {
        if (navController.currentDestination?.id != navController.graph.startDestination) {
            binding?.toolbar?.visibility = View.VISIBLE
        } else {
            binding?.toolbar?.visibility = View.GONE
        }
    }

    override fun onBackPressed() {

        if (navController.currentDestination?.id != navController.graph.startDestination) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}