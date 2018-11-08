package com.example.moviedb.networking.services

import com.example.moviedb.networking.models.Movie
import io.reactivex.Observable
import retrofit2.http.GET

interface MovieService {
    @GET("/movie/popular")
    fun getPopular(): Observable<Result<Movie>>
}