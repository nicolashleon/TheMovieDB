package com.example.moviedb.sources

import com.example.moviedb.models.Movie
import io.reactivex.Observable

interface MovieSource {
    fun getPopularMovies(): Observable<Movie>
    fun saveMovie(movie: Movie): Observable<Movie>
}