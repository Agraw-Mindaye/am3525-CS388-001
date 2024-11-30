package com.example.mealplanner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mealplanner.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val clearAllDataButton = view.findViewById<Button>(R.id.clear_all_data_button)
        val logoutButton = view.findViewById<Button>(R.id.logout_button)

        val recipeDao = AppDatabase.getDatabase(requireContext()).recipeDao()

        // Handle the "Clear All Recipes and Meals" button click
        clearAllDataButton.setOnClickListener {
            showClearConfirmationDialog(recipeDao)
        }

        // Handle the "Logout" button click
        logoutButton.setOnClickListener {
            logoutUser()
        }

        return view
    }

    private fun showClearConfirmationDialog(recipeDao: RecipeDao) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Clear All Recipes and Meals")
            .setMessage("Are you sure you want to delete all recipes and meals? This action cannot be undone.")
            .setPositiveButton("Yes") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    recipeDao.clearAllRecipes()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "All recipes and meals cleared!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun logoutUser() {
        // Sign out the user
        auth.signOut()

        // Redirect to LoginActivity
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)

        // Close MainActivity to prevent back navigation
        requireActivity().finish()
    }
}
