package com.example.moviedb.adapters

import com.example.moviedb.R
import com.example.moviedb.delegates.GenericDelegateAdapter
import com.example.moviedb.delegates.MovieDelegateAdapter
import com.example.moviedb.models.DelegateUIModel
import com.example.moviedb.models.Movie

class MovieAdapter(private val onMovieClickedListener: OnMovieClickedListener) : BaseAdapter(), MovieDelegateAdapter.OnMovieItemListener {

    interface OnMovieClickedListener {
        fun onMovieClicked(m: Movie)
    }

    init {
        addDelegate(MovieDelegateAdapter(this), DelegateUIModel.MOVIE_ITEM)
        addDelegate(GenericDelegateAdapter(R.layout.item_error), DelegateUIModel.ERROR_ITEM)
    }

    override fun onMovieClicked(pos: Int) {
        onMovieClickedListener.onMovieClicked(getItem(pos))
    }
}