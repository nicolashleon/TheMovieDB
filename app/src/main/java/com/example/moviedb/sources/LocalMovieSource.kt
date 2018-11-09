package com.example.moviedb.sources

import com.example.moviedb.models.Movie
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.realm.Realm

class LocalMovieSource : MovieSource {


    override fun getPopularMovies(): Observable<Movie> {
        return Observable.create<List<Movie>> { emitter: ObservableEmitter<List<Movie>> ->
            val realm = Realm.getDefaultInstance()
            try {
                val result = realm.where(Movie::class.java).findAll()
                emitter.onNext(realm.copyFromRealm(result.toList()))
                emitter.onComplete()
            } catch (e: java.lang.IllegalStateException) {
                emitter.onError(e)
            } finally {
                realm.close()
            }
        }.flatMapIterable { it }
    }

    override fun saveMovie(movie: Movie): Observable<Movie> {
        return Observable.create { emitter: ObservableEmitter<Movie> ->
            val realm = Realm.getDefaultInstance()
            try {
                realm.beginTransaction()
                emitter.onNext(realm.copyToRealmOrUpdate(movie))
            } catch (e: java.lang.IllegalStateException) {
                emitter.onError(e)
            } finally {
                realm.commitTransaction()
                realm.close()
                emitter.onComplete()
            }
        }
    }
}