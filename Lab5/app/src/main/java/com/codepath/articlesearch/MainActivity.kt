package com.codepath.articlesearch

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity/"
private val SEARCH_API_KEY = BuildConfig.API_KEY
private val ARTICLE_SEARCH_URL =
    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"

class MainActivity : AppCompatActivity() {
    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private val articles = mutableListOf<DisplayArticle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        articlesRecyclerView = findViewById(R.id.articles)

        // Setup RecyclerView and adapter
        articleAdapter = ArticleAdapter(this, articles)
        articlesRecyclerView.adapter = articleAdapter
        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        // Setup Swipe to Refresh
        swipeRefreshLayout.setOnRefreshListener { fetchDataFromApi() }

        // Register network receiver
        networkReceiver = NetworkReceiver { isConnected ->
            handleNetworkChange(isConnected)
        }
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        // Initial data load
        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        swipeRefreshLayout.isRefreshing = true
        val client = AsyncHttpClient()
        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    parsedJson.response?.docs?.let { list ->
                        lifecycleScope.launch(Dispatchers.IO) {
                            (application as ArticleApplication).db.articleDao().deleteAll()
                            (application as ArticleApplication).db.articleDao().insertAll(
                                list.map {
                                    ArticleEntity(
                                        headline = it.headline?.main,
                                        articleAbstract = it.abstract,
                                        byline = it.byline?.original,
                                        mediaImageUrl = it.mediaImageUrl
                                    )
                                }
                            )
                        }
                    }

                    lifecycleScope.launch {
                        (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                            articles.clear()
                            articles.addAll(databaseList.map { entity ->
                                DisplayArticle(
                                    entity.headline,
                                    entity.articleAbstract,
                                    entity.byline,
                                    entity.mediaImageUrl
                                )
                            })
                            articleAdapter.notifyDataSetChanged()
                        }
                    }

                    swipeRefreshLayout.isRefreshing = false

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun handleNetworkChange(isConnected: Boolean) {
        if (isConnected) {
            Log.d(TAG, "Network is back online, reloading data.")
            Snackbar.make(swipeRefreshLayout, "Back online! Refreshing data...", Snackbar.LENGTH_SHORT).show()
            fetchDataFromApi() // Fetch data again when connectivity is restored
        } else {
            Log.d(TAG, "Network is offline.")
            Snackbar.make(swipeRefreshLayout, "You are offline!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") { fetchDataFromApi() }
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }

    private fun createJson() = Json {
        isLenient = true
        ignoreUnknownKeys = true
        useAlternativeNames = false
    }
}
