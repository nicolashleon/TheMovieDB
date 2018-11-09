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
                emitter.onNext(realm.copyFromRealm(realm.copyToRealmOrUpdate(movie)))
            } catch (e: java.lang.IllegalStateException) {
                emitter.onError(e)
            } finally {
                realm.commitTransaction()
                realm.close()
                emitter.onComplete()
            }
        }
    }

    override fun getMovie(movieId: Int): Observable<Movie> {
        return Observable.create<Movie> { emitter: ObservableEmitter<Movie> ->
            val realm = Realm.getDefaultInstance()
            try {
                val result = realm.where(Movie::class.java).equalTo("id", movieId)
                val movie = result.findFirst()
                if (movie == null) {
                    emitter.onComplete()
                } else {
                    emitter.onNext(realm.copyFromRealm(movie))
                }

            } catch (e: java.lang.IllegalStateException) {
                emitter.onError(e)
            } finally {
                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
                realm.close()
            }
        }
    }
}