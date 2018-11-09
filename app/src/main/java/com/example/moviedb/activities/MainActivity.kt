package com.example.moviedb.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.MovieItemDecorator
import com.example.moviedb.R
import com.example.moviedb.adapters.MovieAdapter
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.models.DelegateUIModel
import com.example.moviedb.models.GenericUIModel
import com.example.moviedb.models.Movie
import com.example.moviedb.repositories.MovieRepository
import com.example.moviedb.viewmodels.MovieViewModel
import io.reactivex.observers.DisposableObserver
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(), MovieAdapter.OnMovieClickedListener {

    private val movieAdapter = MovieAdapter(this)

    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.recyclerview.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(MovieItemDecorator())
        }

    }

    override fun onResume() {
        super.onResume()
        movieAdapter.clearItemsAndNotify()
        dataBinding.swipeRefreshLayout.isRefreshing = true
        addDisposable(MovieViewModel(movieRepository).getPopularMovies().subscribeWith(object :
                DisposableObserver<Movie>() {
            override fun onComplete() {
            }

            override fun onNext(t: Movie) {
                if (dataBinding.swipeRefreshLayout.isRefreshing) {
                    dataBinding.swipeRefreshLayout.isRefreshing = false
                }
                movieAdapter.addItemAndNotify(t)
            }

            override fun onError(e: Throwable) {
                if (dataBinding.swipeRefreshLayout.isRefreshing) {
                    dataBinding.swipeRefreshLayout.isRefreshing = false
                }
                movieAdapter.clearItemsAndNotify()
                movieAdapter.addItemAndNotify(GenericUIModel(DelegateUIModel.ERROR_ITEM))
            }
        }))
    }


    override fun onMovieClicked(m: Movie) {
        startActivity(MovieDetailActivity.getIntent(this, m.id))
    }

}
