package com.omarlabastida.moviedbapi.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetRetrofit {

    private val urlMovie = "https://api.themoviedb.org/3/"//URL Base de la api Movie DB

    fun getRetrofit(): Retrofit { //Funcion para uso de Retrofit
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(urlMovie)
            .build()
    }
}