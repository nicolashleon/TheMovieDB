package com.example.moviedb.sources

import com.example.moviedb.BuildConfig
import com.example.moviedb.models.Movie
import com.example.moviedb.networking.models.Response
import com.example.moviedb.networking.services.MovieService
import io.reactivex.Observable

class RemoteMovieSource(private val movieService: MovieService) : MovieSource {

    override fun getPopularMovies(): Observable<Movie> {
        return movieService.getPopular()
                .flatMapIterable { t: Response<com.example.moviedb.networking.models.Movie> -> t.results }
                .map { t: com.example.moviedb.networking.models.Movie ->
                    Movie(id = t.id, adult = t.adult, backdropPath = BuildConfig.BIG_POSTER_BASE_URL + t.backdropPath,
                            description = t.overview, popularity = t.popularity,
                            posterPath = BuildConfig.POSTER_BASE_URL + t.posterPath, releaseDate = t.releaseDate,
                            title = t.title, video = t.video, score = t.voteAverage, votes = t.voteCount)
                }
    }

    override fun saveMovie(movie: Movie): Observable<Movie> {
        throw UnsupportedOperationException("TMDB API does not support this operation")
    }

    override fun getMovie(movieId: Int): Observable<Movie> {
        throw UnsupportedOperationException("TMDB API does not support this operation")
    }
}
