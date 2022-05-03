package com.omarlabastida.moviedbapi.data.network

import com.omarlabastida.moviedbapi.core.GetRetrofit
import com.omarlabastida.moviedbapi.data.model.apidata.Result
import com.omarlabastida.moviedbapi.data.model.apidata.ResultX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrailerSearch {

    private val retrofit = GetRetrofit.getRetrofit()
    private val apiKey: String = "9698798565b0d0f10c716eed5e16b78b"//ApiKey de la Api

    suspend fun trailerSearch(key: Int): List<ResultX>{// Aqui se busca el trailer de la película seleccionada
        return withContext(Dispatchers.IO){
            val callTrailer= retrofit.create(ApiService::class.java).getTriler("movie/$key/videos?api_key=$apiKey")
            callTrailer.body()?.results?: emptyList()
        }
    }
}