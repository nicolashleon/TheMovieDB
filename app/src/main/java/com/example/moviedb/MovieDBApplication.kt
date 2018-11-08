package com.example.moviedb

import android.app.Application
import com.example.moviedb.networking.interceptors.AuthInterceptor
import com.example.moviedb.networking.services.MovieService
import com.example.moviedb.sources.LocalMovieSource
import com.example.moviedb.sources.RemoteMovieSource
import io.realm.Realm
import okhttp3.OkHttpClient
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieDBApplication : Application() {

    private val appModule = module {
        single { Realm.getDefaultInstance() }
        single { LocalMovieSource(get()) }
        single { RxJava2CallAdapterFactory.create() }
        single { GsonConverterFactory.create() }
        single { OkHttpClient.Builder().addInterceptor(AuthInterceptor()) }
        single {
            Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL_V3)
                .client(get())
                .addConverterFactory(get())
                .addCallAdapterFactory(get())
                .build()
        }
        single {
            val retrofit: Retrofit = get()
            retrofit.create(MovieService::class.java)
        }
        single { RemoteMovieSource(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}