package com.example.moviedb

import android.app.Application
import com.example.moviedb.networking.interceptors.AuthInterceptor
import com.example.moviedb.networking.services.MovieService
import com.example.moviedb.repositories.MovieRepository
import com.example.moviedb.sources.LocalMovieSource
import com.example.moviedb.sources.RemoteMovieSource
import io.realm.Realm
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieDBApplication : Application() {

    private val appModule by lazy {
        module {
            single { Realm.init(this@MovieDBApplication) }
            single { Realm.getDefaultInstance() }
            single { LocalMovieSource(get()) }
            single {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
                val builder = OkHttpClient.Builder()
                        .addInterceptor(AuthInterceptor())
                if (BuildConfig.DEBUG) {
                    builder.addInterceptor(httpLoggingInterceptor)
                }

                builder.build()
            }
            single {
                Retrofit.Builder().baseUrl(HttpUrl.parse(BuildConfig.API_BASE_URL_V3)!!)
                        .client(get())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }
            single {
                val retrofit: Retrofit = get()
                retrofit.create(MovieService::class.java)
            }
            single { RemoteMovieSource(get()) }
            single { MovieRepository(get(), get()) }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin(this, listOf(appModule))
    }
}