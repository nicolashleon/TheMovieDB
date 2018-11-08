package com.example.moviedb.sources

import com.example.moviedb.models.Movie
import io.reactivex.Completable
import io.reactivex.Observable

interface MovieSource {
    fun getPopularMovies(): Observable<Movie>
    fun saveMovies(movies: List<Movie>): Completable
    fun saveMovie(movie: Movie): Completable
}