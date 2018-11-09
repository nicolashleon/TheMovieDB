package com.example.moviedb.sources

import com.example.moviedb.BuildConfig
import com.example.moviedb.models.Movie
import com.example.moviedb.networking.models.Response
import com.example.moviedb.networking.services.MovieService
import io.reactivex.Completable
import io.reactivex.Observable

class RemoteMovieSource(private val movieService: MovieService) : MovieSource {

    override fun getPopularMovies(): Observable<Movie> {
        return movieService.getPopular()
                .flatMapIterable { t: Response<com.example.moviedb.networking.models.Movie> -> t.results }
                .map { t: com.example.moviedb.networking.models.Movie ->
                    Movie(t.id, t.adult, BuildConfig.POSTER_BASE_URL + t.backdropPath,
                            t.overview, t.popularity,
                            BuildConfig.POSTER_BASE_URL + t.posterPath, t.releaseDate,
                            t.title, t.video, t.voteAverage, t.voteCount)
                }
    }

    override fun saveMovies(movies: List<Movie>): Completable {
        throw UnsupportedOperationException("TMDB API does not support this operation")
    }

    override fun saveMovie(movie: Movie): Completable {
        throw UnsupportedOperationException("TMDB API does not support this operation")
    }
}
