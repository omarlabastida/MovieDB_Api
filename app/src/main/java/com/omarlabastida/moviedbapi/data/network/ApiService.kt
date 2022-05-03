package com.omarlabastida.moviedbapi.data.network

import com.omarlabastida.moviedbapi.data.model.apidata.MovieData
import com.omarlabastida.moviedbapi.data.model.apidata.MovieTrailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getMovie(@Url url: String): Response<MovieData>
    @GET
    suspend fun getTriler(@Url url: String): Response<MovieTrailer>
}