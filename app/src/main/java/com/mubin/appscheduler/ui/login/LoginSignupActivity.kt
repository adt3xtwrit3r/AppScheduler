package com.mubin.appscheduler.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.mubin.appscheduler.R
import com.mubin.appscheduler.databinding.ActivityLoginSignupBinding

class LoginSignupActivity : AppCompatActivity() {

    private var binding: ActivityLoginSignupBinding? = null

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initNavGraph()

    }

    private fun initNavGraph() {

        navController = findNavController(R.id.navHostFragmentLogin)

    }


}