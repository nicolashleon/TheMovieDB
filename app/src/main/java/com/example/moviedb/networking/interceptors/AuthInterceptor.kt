package com.example.moviedb.networking.interceptors

import com.example.moviedb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {

    companion object {
        private const val API_KEY_PARAM = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url().toString().contains(BuildConfig.API_BASE_URL_V3)) {
            val url = chain.request().url()
                .newBuilder()
                .addQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY)
                .build()
            return chain.proceed(chain.request().newBuilder().url(url).build())
        }
        return chain.proceed(request)
    }
}