package me.xh.wp.http

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {

    private const val TIME_OUT = 15L

    private val logger by lazy {
        HttpLoggingInterceptor { message -> Log.e("HTTP_LOG", message) }.also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(logger)
        .build()

    val wallpaperRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://wallhaven.cc/api/v1/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}