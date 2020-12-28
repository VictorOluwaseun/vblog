package com.decagon.vblog.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.decagon.vblog.R
import com.decagon.vblog.databinding.ActivityMainBinding
/*
* MainActivity class
* */
class MainActivity : AppCompatActivity() {
    /* promise to initialise activitymainbinding*/
    private lateinit var binding : ActivityMainBinding
    /*The onCreate callback*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView through binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Set action nav bar 
        setupActionBarWithNavController(findNavController(R.id.navHostFragment))

    }
        // The navigation up
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp()|| super.onSupportNavigateUp()
    }
}