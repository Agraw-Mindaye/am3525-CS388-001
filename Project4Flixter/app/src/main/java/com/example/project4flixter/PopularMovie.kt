package com.example.project4flixter

import com.google.gson.annotations.SerializedName

// Model for storing a single movie from the TMDb API
class PopularMovie {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("vote_average")
    var voteAverage: Double? = null
}
