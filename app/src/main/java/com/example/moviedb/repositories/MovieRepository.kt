package com.example.moviedb.repositories

import com.example.moviedb.models.Movie
import com.example.moviedb.sources.LocalMovieSource
import com.example.moviedb.sources.RemoteMovieSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieRepository(private val localMovieSource: LocalMovieSource,
                      private val remoteMovieSource: RemoteMovieSource) {

    fun getPopular(): Observable<Movie> {
        return remoteMovieSource.getPopularMovies()
                .doOnEach {
                    val value = it.value
                    if (!it.isOnError && value != null) {
                        localMovieSource.saveMovie(value)
                    }
                }
                .doOnError { localMovieSource.getPopularMovies() }
                .switchIfEmpty(Observable.error<Movie>(Exception("Database is empty")))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}