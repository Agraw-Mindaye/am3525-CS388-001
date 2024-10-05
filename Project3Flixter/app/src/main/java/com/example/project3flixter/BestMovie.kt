package com.example.project3flixter

import com.google.gson.annotations.SerializedName

class BestMovie {
    @JvmField
    @SerializedName("title")
    var title: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("overview")
    var overview: String? = null
}
