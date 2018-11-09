package com.example.moviedb.models

/**
 * Code adapted from https://github.com/sockeqwe/AdapterDelegates
 * http://hannesdorfmann.com/android/adapter-delegates
 */
interface DelegateUIModel {
    companion object {
        const val MOVIE_ITEM = 1
        const val ERROR_ITEM = 2
    }
    val viewType: Int
}