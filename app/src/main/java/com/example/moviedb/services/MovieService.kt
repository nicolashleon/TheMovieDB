package com.example.moviedb.services

import com.example.moviedb.models.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("/movie/popular")
    fun getPopular(): Observable<Result<Movie>>

    @GET("/movie/{id}")
    fun getMovie(@Path("id") id: Int): Observable<Result<Movie>>
}