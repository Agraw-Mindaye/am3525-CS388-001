package com.example.project4flixter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.project4flixter.MovieDetailsActivity
import com.example.project4flixter.OnListFragmentInteractionListener
import com.example.project4flixter.PopularMovie
import com.example.project4flixter.PopularMoviesRecyclerViewAdapter
import com.example.project4flixter.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject

class PopularMoviesFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular_movies_list, container, false)
        val progressBar = view.findViewById<ContentLoadingProgressBar>(R.id.progress)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        val context = view.context

        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams().apply {
            put("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed")  // Use the API key from apikey.properties
        }

        client.get("https://api.themoviedb.org/3/movie/popular", params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                progressBar.hide()

                val resultsJSON = json.jsonObject.getJSONArray("results")
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<PopularMovie>>() {}.type
                val movies: List<PopularMovie> = gson.fromJson(resultsJSON.toString(), arrayMovieType)

                recyclerView.adapter = PopularMoviesRecyclerViewAdapter(movies, this@PopularMoviesFragment)

                Log.d("PopularMoviesFragment", "Response successful")
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressBar.hide()
                t?.message?.let { Log.e("PopularMoviesFragment", errorResponse) }
            }
        })
    }

    override fun onItemClick(item: PopularMovie) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("movie_title", item.title)
        intent.putExtra("movie_overview", item.overview)
        intent.putExtra("release_date", item.releaseDate)
        intent.putExtra("vote_average", item.voteAverage)
        intent.putExtra("poster_path", item.posterPath)
        startActivity(intent)
    }
}
