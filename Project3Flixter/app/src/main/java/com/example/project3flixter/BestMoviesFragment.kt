package com.example.project3flixter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject

class BestMoviesFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_best_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams().apply {
            put("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed")
        }

        client["https://api.themoviedb.org/3/movie/now_playing", params, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                progressBar.hide()

                // parse JSON into models
                val resultsJSON = json.jsonObject.getJSONArray("results")
                val moviesRawJSON  = resultsJSON.toString()

                // parse the JSON array into a list of objects
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<BestMovie>>() {}.type
                val models: List<BestMovie> = gson.fromJson(moviesRawJSON, arrayMovieType)

                recyclerView.adapter = BestMovieRecyclerViewAdapter(models, this@BestMoviesFragment)

                // Log success
                Log.d("BestMoviesFragment", "Response successful")
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                progressBar.hide()

                // Log the error
                t?.message?.let {
                    Log.e("BestMoviesFragment", errorResponse)
                }
            }
        }]

    }

    override fun onItemClick(item: BestMovie) {
        Toast.makeText(context, "Movie: " + item.title, Toast.LENGTH_LONG).show()
    }
}
