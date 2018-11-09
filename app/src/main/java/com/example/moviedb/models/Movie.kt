package com.example.moviedb.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Movie(@PrimaryKey var id: Int = 0,
                 var adult: Boolean = false,
                 var backdropPath: String? = null,
                 var description: String = "",
                 var popularity: Double = 0.0,
                 var posterPath: String? = null,
                 var releaseDate: String = "",
                 var title: String = "",
                 var video: Boolean = false,
                 var score: Double = 0.0,
                 var votes: Int = 0) : RealmObject(), DelegateUIModel {

    override val viewType: Int
        get() = DelegateUIModel.MOVIE_ITEM
}