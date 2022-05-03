package com.omarlabastida.moviedbapi.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omarlabastida.moviedbapi.data.model.apidata.Result
import com.omarlabastida.moviedbapi.R

class MovieAdapter(val movies: List<Result>, private val itemClickListener: onMovieClickListener): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    interface onMovieClickListener{
        fun onRecyclerClick(movie: Result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.view_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        val itemClick:onMovieClickListener = itemClickListener
        holder.bind(movie, itemClick)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var cover = view.findViewById<ImageView>(R.id.imageMovie)
        private var title = view.findViewById<TextView>(R.id.titleMovie)
        private val imageUrl = "https://image.tmdb.org/t/p/w500"

        fun bind (movie: Result, itemClick: onMovieClickListener){
            title.text= movie.title
                Glide.with(cover.context).load(imageUrl+movie.poster_path).into(cover)


            itemView.setOnClickListener{
                itemClick.onRecyclerClick(movie)
            }
        }
    }
}