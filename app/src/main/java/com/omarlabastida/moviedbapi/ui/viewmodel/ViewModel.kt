package com.omarlabastida.moviedbapi.ui.viewmodel


import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarlabastida.moviedbapi.data.domain.GetMovieUseCae
import com.omarlabastida.moviedbapi.data.domain.GetTrailerUseCase
import com.omarlabastida.moviedbapi.data.model.apidata.Result
import com.omarlabastida.moviedbapi.data.model.apidata.ResultX
import com.omarlabastida.moviedbapi.ui.view.MainActivity
import com.omarlabastida.moviedbapi.ui.view.MovieFeatures
import kotlinx.coroutines.launch
import java.nio.file.AtomicMoveNotSupportedException

class ViewModel: ViewModel() {

    var movieModel = MutableLiveData<List<Result>>()
    var trailerModel = MutableLiveData<List<ResultX>>()
    var loading = MutableLiveData<Boolean>()

    var getMovieUseCase = GetMovieUseCae()
    var getTrailerUseCase = GetTrailerUseCase()

    fun onCreate(select:String, page: Int){
        viewModelScope.launch {
            loading.postValue(true)
            val movieData: List<Result>? = getMovieUseCase(select, page)
            if(!movieData.isNullOrEmpty()){
                movieModel.postValue(movieData)
                loading.postValue(false)
            }
        }
    }

    fun trailerSearch(key:Int){
        viewModelScope.launch {
            loading.postValue(true)
            val trailerList: List<ResultX>? = getTrailerUseCase(key)
            if(!trailerList.isNullOrEmpty()){
                trailerModel.postValue(trailerList)
                loading.postValue(false)
            }
        }
    }
}