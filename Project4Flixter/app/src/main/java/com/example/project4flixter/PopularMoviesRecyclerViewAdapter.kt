package com.example.project4flixter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project3flixter.OnListFragmentInteractionListener
import com.example.project4flixter.R

class PopularMoviesRecyclerViewAdapter(
    private val movies: List<PopularMovie>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<PopularMoviesRecyclerViewAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_popular_movie, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: PopularMovie? = null
        val mMovieImage: ImageView = mView.findViewById(R.id.movie_poster)
        val mMovieTitle: TextView = mView.findViewById(R.id.movie_title)

        override fun toString(): String {
            return mMovieTitle.text.toString()
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title

        // Use Glide to load the movie poster
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Glide.with(holder.mView)
            .load(posterUrl)
            .centerInside()
            .into(holder.mMovieImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
