package com.example.project3flixter

import com.example.project4flixter.PopularMovie

/**
 * This interface is used by the [BestSellerMovieRecyclerViewAdapter] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [BestSellerMoviesFragment]
 */
interface OnListFragmentInteractionListener {
    fun onItemClick(item: PopularMovie)
}
