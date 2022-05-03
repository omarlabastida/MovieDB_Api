package com.omarlabastida.moviedbapi.data.domain

import com.omarlabastida.moviedbapi.data.model.apidata.Result
import com.omarlabastida.moviedbapi.data.MovieRepository

class GetMovieUseCae {

    private val repository = MovieRepository()

    suspend operator fun invoke(select: String, page: Int): List<Result> = repository.getAllMovies(select, page)

}