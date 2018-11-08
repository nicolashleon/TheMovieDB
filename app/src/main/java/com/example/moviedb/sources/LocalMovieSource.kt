package com.example.moviedb.sources

import com.example.moviedb.models.Movie
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Observable
import io.realm.Realm

class LocalMovieSource(private val realm: Realm) : MovieSource {
    override fun getPopularMovies(): Observable<Movie> {
        val result = realm.where(Movie::class.java).findAll()
        return result.asChangesetObservable()
                .flatMapIterable { it.collection }
    }

    override fun saveMovies(movies: List<Movie>): Completable {
        return Completable.create { emitter: CompletableEmitter ->
            realm.executeTransactionAsync({ realm.apply { copyToRealmOrUpdate(movies) } },
                    { emitter.onComplete() },
                    { error -> emitter.onError(error) })
        }
    }

    override fun saveMovie(movie: Movie): Completable {
        return Completable.create { emitter: CompletableEmitter ->
            realm.executeTransactionAsync({ realm.apply { copyToRealmOrUpdate(movie) } },
                    { emitter.onComplete() },
                    { error -> emitter.onError(error) })
        }
    }
}