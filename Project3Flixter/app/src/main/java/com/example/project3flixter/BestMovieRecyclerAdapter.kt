package com.example.project3flixter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project3flixter.R.id

class BestMovieRecyclerViewAdapter(
    private val movies: List<BestMovie>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<BestMovieRecyclerViewAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_best_movie, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: BestMovie? = null
        val mMovieImage: ImageView = mView.findViewById<View>(id.image_view) as ImageView
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView

        override fun toString(): String {
            return mMovieTitle.toString()
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mDescription.text = movie.overview

        // make a full URL for the poster image
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
