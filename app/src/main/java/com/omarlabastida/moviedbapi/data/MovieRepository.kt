package com.omarlabastida.moviedbapi.data

import com.omarlabastida.moviedbapi.data.network.MovieSearch
import com.omarlabastida.moviedbapi.data.model.apidata.Result
import com.omarlabastida.moviedbapi.data.model.apidata.ResultX
import com.omarlabastida.moviedbapi.data.network.TrailerSearch

class MovieRepository {

    private val MovieSearch = MovieSearch()
    private val TrailerSearch = TrailerSearch()

    suspend fun getAllMovies(select:String, page:Int): List<Result>{
        val movieData: List<Result> = MovieSearch.movieSearch(select, page)
        return movieData
    }

    suspend fun getTrailer(key: Int): List<ResultX>{
        val trailer: List<ResultX> = TrailerSearch.trailerSearch(key)
        return trailer
    }


}