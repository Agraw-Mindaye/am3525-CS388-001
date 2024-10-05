package com.example.project3flixter

/**
 * This interface is used by the [BestSellerMovieRecyclerViewAdapter] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [BestSellerMoviesFragment]
 */
interface OnListFragmentInteractionListener {
    fun onItemClick(item: BestMovie)
}
