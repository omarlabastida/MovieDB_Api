package com.omarlabastida.moviedbapi.data.network

import android.widget.Toast
import com.omarlabastida.moviedbapi.core.GetRetrofit
import com.omarlabastida.moviedbapi.core.GetRetrofit.getRetrofit
import com.omarlabastida.moviedbapi.data.model.apidata.MovieData
import com.omarlabastida.moviedbapi.data.model.apidata.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlinx.coroutines.withContext

class MovieSearch {

    private val retrofit = getRetrofit()
    private val apiKey: String = "9698798565b0d0f10c716eed5e16b78b"//ApiKey de la Api

    suspend fun movieSearch(select:String, page:Int): List<Result>{// Aqui se van a buscar las peliculas
        return withContext(Dispatchers.IO){
            val call=retrofit.create(ApiService::class.java).getMovie("movie/$select?page=$page&api_key=$apiKey")
            call.body()?.results?: emptyList()
        }
    }
}
