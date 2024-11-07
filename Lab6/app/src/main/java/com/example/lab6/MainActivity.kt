package com.example.lab6

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.lab6.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d("MainActivity", "onCreate called")

        // Initialize BottomNavigationView and set default fragment
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_articles -> {
                    replaceFragment(ArticleListFragment())
                    true
                }
                R.id.nav_books -> {
                    replaceFragment(BestSellerBooksFragment())
                    true
                }
                else -> false
            }
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.nav_books
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy called")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Check the new orientation and make layout adjustments as needed
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Code to update layout or adjust UI for landscape orientation
            Log.d("MainActivity", "Orientation changed to landscape")
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Code to update layout or adjust UI for portrait orientation
            Log.d("MainActivity", "Orientation changed to portrait")
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.article_frame_layout, fragment)
        fragmentTransaction.commit()
    }

}