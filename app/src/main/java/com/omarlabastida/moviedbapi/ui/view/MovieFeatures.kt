package com.omarlabastida.moviedbapi.ui.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.omarlabastida.moviedbapi.R
import com.squareup.picasso.Picasso

class MovieFeatures : YouTubeBaseActivity() {

    var apiKey_Youtube: String = "AIzaSyBuhL-qiU8YFoKECbcIG4D65HwYRpgwXl8"
    lateinit var youtubePlayer: YouTubePlayerView
    lateinit var buttonPlayer: Button
    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Activity de caracteristicas y trailer
        println("Entra a la nueva activity")
        setContentView(R.layout.activity_movie_features)


        var imageMovieL= findViewById<ImageView>(R.id.imageMovie)
        var nameMovieL= findViewById<TextView>(R.id.nameMovie)

        var titleMovieL= findViewById<TextView>(R.id.titleMovie)
        var descriptionMovieL = findViewById<TextView>(R.id.descriptionMovie)
        youtubePlayer= findViewById(R.id.youtubePlayer)
        buttonPlayer = findViewById(R.id.buttonPlayer)

        //Variables de la activity anterior
        var imageMovie=intent.getStringExtra("imageMovie")
        var titleMovie=intent.getStringExtra("nameMovie")
        var dateMovie=intent.getStringExtra("dateMovie")
        var languajeMovie=intent.getStringExtra("languajeMovie")
        var voteMovie=intent.getStringExtra("voteMovie")
        var descriptionMovie=intent.getStringExtra("descriptionMovie")
        var apiKey=intent.getStringExtra("apiKey")

        titleMovieL.text ="Titulo Original: $titleMovie \n\n"
        nameMovieL.text = "Año: $dateMovie \n\n Idioma: $languajeMovie \n\n Calificación: $voteMovie"

        descriptionMovieL.text="Sinopsis: $descriptionMovie"
        Picasso.get().load(imageMovie).into(imageMovieL)
        //Reproductor de Youtube
        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(apiKey)
            }
            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@MovieFeatures, "Reproducción Fallida", Toast.LENGTH_SHORT).show()
            }
        }
        buttonPlayer.setOnClickListener {
            youtubePlayer.initialize(apiKey_Youtube, youtubePlayerInit)
        }
    }
}