package com.example.moviedb.viewmodels

import com.example.moviedb.models.Movie
import com.example.moviedb.repositories.MovieRepository
import io.reactivex.Observable

class MovieViewModel(private val repository: MovieRepository) {
    fun getPopularMovies(): Observable<Movie> = repository.getPopular()
}