package com.example.mealplanner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set up Logout Button
        val logoutButton = view.findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {
            logoutUser()
        }

        return view
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
