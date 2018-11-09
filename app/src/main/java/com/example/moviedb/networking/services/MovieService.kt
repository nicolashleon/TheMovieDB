package com.example.moviedb.networking.services

import com.example.moviedb.networking.models.Movie
import com.example.moviedb.networking.models.Response
import io.reactivex.Observable
import retrofit2.http.GET

interface MovieService {
    @GET("/3/movie/popular")
    fun getPopular(): Observable<Response<Movie>>
}