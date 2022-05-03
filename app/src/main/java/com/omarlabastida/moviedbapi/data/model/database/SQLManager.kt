package com.omarlabastida.moviedbapi.data.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
import com.omarlabastida.moviedbapi.data.model.apidata.Result

class SQLManager(context:Context):SQLiteOpenHelper(context, "data_base", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        //Se crea una tabla con los datos necesarios para la activity
        db!!.execSQL("CREATE TABLE movieTable(id NUMERIC(20), title VARCHAR(30), poster_path VARCHAR (150))")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun addMovie(context: Context, movieServer: Result): Boolean{
        var response = true
        var contenValue = ContentValues()
        contenValue.put("id", movieServer.id)
        contenValue.put("title", movieServer.title)
        contenValue.put("poster_path", movieServer.poster_path)
        var db = SQLManager(context)
        var manager = db.writableDatabase
        try{
            manager.insert("movieTable", null, contenValue)
        }
        catch (e: Exception){
            println(e.message)
            response = false
        }
        finally {
            db.close()
        }
        return response
    }
    //Funcion de lectura de la base de datos
    fun readMovie (): Cursor {
        val db: SQLiteDatabase =this.writableDatabase
        val read = db.rawQuery("SELECT * FROM movieTable", null)
        return read
    }
    //Funcion para borrar la tabla de la base de datos
    fun deleteMovie(){
        val db: SQLiteDatabase = this.writableDatabase
        val deleteData = db.delete("movieTable", null, null)
    }

}