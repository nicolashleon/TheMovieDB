package com.example.moviedb.repositories

import com.example.moviedb.BuildConfig
import com.example.moviedb.services.MovieService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL_V3)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    private val service = retrofit.create(MovieService::class.java)

    fun getMovie(id: Int) {
        
    }

    fun getPopular() {

    }
}