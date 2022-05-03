package com.omarlabastida.moviedbapi.data.domain

import com.omarlabastida.moviedbapi.data.MovieRepository
import com.omarlabastida.moviedbapi.data.model.apidata.ResultX

class GetTrailerUseCase {

    private val repository = MovieRepository()

    suspend operator fun invoke(key: Int): List<ResultX> = repository.getTrailer(key)

}