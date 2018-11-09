package com.example.moviedb.adapters

import com.example.moviedb.R
import com.example.moviedb.delegates.GenericDelegateAdapter
import com.example.moviedb.delegates.MovieDelegateAdapter
import com.example.moviedb.models.DelegateUIModel

class MovieAdapter : BaseAdapter() {
    init {
        addDelegate(MovieDelegateAdapter(), DelegateUIModel.MOVIE_ITEM)
        addDelegate(GenericDelegateAdapter(R.layout.item_error), DelegateUIModel.ERROR_ITEM)
    }
}