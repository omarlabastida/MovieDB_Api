package com.omarlabastida.moviedbapi.data.model.apidata

data class MovieData(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)