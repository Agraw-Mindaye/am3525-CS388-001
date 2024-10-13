package com.example.project4flixter

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.project4flixter.R

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieTitle = intent.getStringExtra("movie_title")
        val movieOverview = intent.getStringExtra("movie_overview")
        val releaseDate = intent.getStringExtra("release_date")
        val voteAverage = intent.getDoubleExtra("vote_average", 0.0)
        val posterPath = intent.getStringExtra("poster_path")

        val titleView = findViewById<TextView>(R.id.movie_title)
        val overviewView = findViewById<TextView>(R.id.movie_overview)
        val releaseDateView = findViewById<TextView>(R.id.release_date)
        val voteAverageView = findViewById<TextView>(R.id.vote_average)
        val posterImageView = findViewById<ImageView>(R.id.movie_poster)

        titleView.text = movieTitle
        overviewView.text = movieOverview
        releaseDateView.text = "Release Date: $releaseDate"
        voteAverageView.text = "Rating: $voteAverage/10"

        // Load the poster image using Glide
        val posterUrl = "https://image.tmdb.org/t/p/w500$posterPath"
        Glide.with(this)
            .load(posterUrl)
            .centerInside()
            .into(posterImageView)
    }
}
