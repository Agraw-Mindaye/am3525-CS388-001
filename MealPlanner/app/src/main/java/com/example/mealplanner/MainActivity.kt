package com.example.mealplanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Check if the user is logged in
        if (auth.currentUser == null) {
            // Redirect to LoginActivity if the user is not authenticated
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish() // Close MainActivity so the user cannot return here without logging in
            return
        }

        // User is authenticated, set up Bottom Navigation and NavController
        setupNavigation()
    }

    private fun setupNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set up the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Link the BottomNavigationView with the NavController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}
