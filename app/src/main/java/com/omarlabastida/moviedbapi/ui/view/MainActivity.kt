package com.omarlabastida.moviedbapi.ui.view

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omarlabastida.moviedbapi.R
import com.omarlabastida.moviedbapi.data.adapter.MovieAdapter
import com.omarlabastida.moviedbapi.data.model.apidata.Result
import com.omarlabastida.moviedbapi.data.model.apidata.ResultX
import com.omarlabastida.moviedbapi.data.model.database.SQLManager
import com.omarlabastida.moviedbapi.ui.viewmodel.ViewModel

class MainActivity : AppCompatActivity(), MovieAdapter.onMovieClickListener {

    private lateinit var adapter: MovieAdapter//Variable Adapter
    private lateinit var movieSelect: Result//Variable de pelicula seleccionada
    private lateinit var recyclerView: RecyclerView//Declaracion para la vista del Recycler View
    private val movieViewModel: ViewModel by viewModels() //View Model
    private val movieList= mutableListOf<Result>()//Lista movieList de tipo Result para el recyclerView
    private val trilerList = mutableListOf<ResultX>()//Lista de trailers para la next Activity
    private val totalPages= 500//Paginas totales del api
    private var page: Int = 1//Pagina inicial del api
    private var select: String = "popular" //Selector de categoria popular o mas vistos
    private var conection: Boolean = true// Indicador de conexión a internet
    private val atributes = arrayOf("Most Popular", "Playing Now")// Atributos de selección del Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declaración de Vistas
        var next: Button = findViewById(R.id.next)//Botón Next Activity
        var preview: Button = findViewById(R.id.preview)//Botón Preview Activity
        val spinner: Spinner = findViewById(R.id.spinner)// Spinner desplegable
        val progressBar: ProgressBar = findViewById(R.id.progressBar)//Loading
        recyclerView = findViewById(R.id.recyclerView)//RecyclerView


        connection()//Test de conexíon

        if(conection==true){
            movieViewModel.onCreate(select, page)//Si existe conexón a internet accede a ellos atraves de retrofit
        }
        else if (conection ==false){//Si no existe internet recupera los datos de la ultima conexión
            println("Acceso a la base de datos por ausencia de internet")
            var datosLeidos = SQLManager(this).readMovie()
            while (datosLeidos.moveToNext()){
                var movieSave= Result(null,null,null,datosLeidos.getInt(0),
                    null,null,null,null,datosLeidos.getString(2),
                    null,datosLeidos.getString(1),null,null,null)
                //Editable para acceder a más atributos
                movieList.add(movieSave)
            }
            println(movieList)
        }
        recyclerView() //Se inicia el recicler view

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, atributes)
        spinner.adapter= arrayAdapter

        movieViewModel.movieModel.observe(this, Observer {
            SQLManager(this).deleteMovie()//Se borran los datos de la base anteriores
            var movieSize = it.size-1//Se determina la longitud del List<Result>
            movieList.clear() //Se limpia la lista movieList antes de agregar los objetos Nuevos en la base
            for (cont in 0..movieSize){//Anexa la lista de peliculas a la base de datos
                var movieSave= Result(null,null,null,it.get(cont).id,
                    null,null,null,null,it.get(cont).poster_path,
                    null,it.get(cont).title,null,null,null)
                var response = SQLManager(this).addMovie(this, movieSave)//Entra a la base de datos
                //Editable para almacenar mas atributos
                if(response == true) println("Datos Almacenados correctamente")
                else{ println("Datos Almacenados incorrectamente")}
            }

            movieList.addAll(it) // Se le agrega la lista de objetos movieData
            adapter.notifyDataSetChanged()//se notifica al adapter
            recyclerView()//Se refresca el recyclerView
        })
        movieViewModel.trailerModel.observe(this, Observer {
            trilerList.clear() //Se limpia la lista trilerList antes de agregarle los objetos
            trilerList.addAll(it) // Se le agrega la lista de objetos movieData
            nextActivity(movieSelect)//se llama a la funcion de cambio de activity
        })
        movieViewModel.loading.observe(this, Observer {
            progressBar.isVisible = it //Se coloca en visible o no visible la progress bar
        })

        //Spinner de selección "Most Popular" o "Playing Now"
        spinner.onItemSelectedListener=object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (atributes[position]){
                    "Most Popular"-> select = "popular"
                    "Playing Now" -> select = "now_playing"
                }
                connection()
                if (conection==true) {
                    movieViewModel.onCreate(select, page)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?){}
        }

        //Botón de siguiente listado
        next.setOnClickListener {
            connection()
            if(page==totalPages) Toast.makeText(this, "No hay paginas siguientes", Toast.LENGTH_SHORT).show()
            else{ if (conection==true) {
                    page++
                    println("pagina $page")
                    movieViewModel.onCreate(select, page)
                    Toast.makeText(this, "Pagina $page de $totalPages", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        //Botón de listado previo
        preview.setOnClickListener {
            connection()
            if(page==1){
                Toast.makeText(this, "No hay paginas anteriores", Toast.LENGTH_SHORT).show()
            }
            else{
                if (conection==true) {
                    page--
                    println("pagina $page")
                    movieViewModel.onCreate(select, page)
                    recyclerView() }
                Toast.makeText(this, "Pagina $page de $totalPages", Toast.LENGTH_SHORT).show()
            }
        }



    }

    //Click sobre la película deseada
    override fun onRecyclerClick(movie: Result) {
        connection()
        if(conection==true){
            movieViewModel.trailerSearch(movie.id)
            movieSelect = movie
        }
    }

    //Funcion del recyclerView
    fun recyclerView() {
        adapter = MovieAdapter(movieList, this)
        var layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter= adapter
    }

    //Función de cambio de activity
    fun nextActivity(movie: Result){
        var longitude = trilerList.size
        println("longitud es $longitude")
        var cont=0
        if (longitude>0){
            while (trilerList[cont].type!="Trailer") {
                if (cont <= longitude) {
                    cont++
                    println("key ${trilerList[cont]}")
                }
            }
            println("llave en ($cont) :${trilerList[cont].key}")
            val imageUrl = "https://image.tmdb.org/t/p/w500"
            val intent= Intent(this, MovieFeatures::class.java)
            var imageMovie:String = imageUrl+movie.poster_path
            intent.putExtra("imageMovie",imageMovie)
            intent.putExtra("nameMovie", movie.original_title)
            intent.putExtra("descriptionMovie", movie.overview)
            intent.putExtra("dateMovie", movie.release_date)
            intent.putExtra("languajeMovie", movie.original_language)
            intent.putExtra("voteMovie", movie.vote_average.toString())
            intent.putExtra("apiKey", trilerList[cont].key)
            println("image: $imageMovie, name ${movie.original_title}, description ${movie.overview}")
            println("date ${movie.release_date} languaje ${movie.original_language} vote ${movie.vote_average}")
            println("trailer ${trilerList[cont].key} ")
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Sin Información Disponible", Toast.LENGTH_SHORT).show()
        }
    }

    //Función de test de conexión
    fun connection(){
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            println("SI HAy internet")
            conection = true
        } else {
            Toast.makeText(this@MainActivity,"SIN CONEXION A INTERNET", Toast.LENGTH_SHORT).show()
            println("no hay internet")
            conection = false
        }
    }
}