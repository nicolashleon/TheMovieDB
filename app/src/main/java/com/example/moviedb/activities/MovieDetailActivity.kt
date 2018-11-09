package com.example.moviedb.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityMovieDetailBinding
import com.example.moviedb.models.Movie
import com.example.moviedb.viewmodels.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import io.reactivex.observers.DisposableObserver

class MovieDetailActivity : BaseActivity() {

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"
        fun getIntent(context: Context, movieId: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }

        @BindingAdapter("bind_backImage")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_movies_24px)
                        .error(R.drawable.ic_movies_24px)
                        .into(view)
            }
        }

        @BindingAdapter("bind_votes")
        @JvmStatic
        fun loadVotes(view: TextView, votes: Int) {
            val text = view.context.getString(R.string.txt_votes, votes)
            view.text = text
        }


        @BindingAdapter("bind_popularity")
        @JvmStatic
        fun loadPopularity(view: TextView, popularity: Double?) {
            val text = view.context.getString(R.string.txt_popularity, popularity.toString())
            view.text = text
        }

        @BindingAdapter("bind_score")
        @JvmStatic
        fun loadScore(view: TextView, score: Double?) {
            val text = view.context.getString(R.string.txt_score, score.toString())
            view.text = text
        }
    }

    private lateinit var dataBinding: ActivityMovieDetailBinding

    private val movieId: Int by lazy { intent.getIntExtra(MOVIE_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        setSupportActionBar(dataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        addDisposable(MovieViewModel(movieRepository).getMovie(movieId).subscribeWith(object :
                DisposableObserver<Movie>() {
            override fun onComplete() {
            }

            override fun onNext(t: Movie) {
                dataBinding.movie = t
            }

            override fun onError(e: Throwable) {
                Snackbar.make(dataBinding.collapsingToolbarLayout, R.string.error_fetch_movie, Snackbar.LENGTH_LONG)
                        .show()
            }
        }))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
