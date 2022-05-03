# Aplicación Movie DB Api

#####Características
-Aplicación de listado de películas consumidas con Retrofit a traves de la Api
https://api.themoviedb.org/3/
-La aplicación despliega un listado de películas con imagen y titulo en un recyclerView de 2 columnas
-Al acceder a una pelicula seleccionada la aplicación cambia de actividad y despliega el *Titulo original, año de estreno, idioma, calificación, poster, sinopsis y trailer *
-Cuando la aplicación no tiene acceso a internet, despiega la ultima lista de peliculas a la que se accesó, almacenada en una base de datos SQL
-Arquitectura mvvm

###Mejoras y cómo implementarlas

######Más vistas cuando no se tiene acceso a internet
-Por cada vista que se recorra durante la conexión a internet, almacenarlas en la base de datos

######Acceso a las características sin conexión a internet
-De la base de datos creada, faltaría almacenar los datos deplegados en las características y cargarlos en la siguiente Activity.
