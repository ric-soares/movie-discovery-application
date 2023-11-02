package com.example.moviediscoveryapplication.network

import android.content.Context
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.BASE_URL
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    context: Context,
    connectivityInterceptor: ConnectivityInterceptor,
    modifyUrlInterceptor: ModifyUrlInterceptor,
    loggingInterceptor: LoggingInterceptor,
    cacheInterceptor: CacheInterceptor
) {

    private val okHttpClient = OkHttpClient.Builder()
        .cache(Cache(File(context.cacheDir, "http-cache"), 10L * 1024L * 1024L))
        .addNetworkInterceptor(cacheInterceptor)
        .addInterceptor(connectivityInterceptor)
        .addInterceptor(modifyUrlInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(5, 500, TimeUnit.MILLISECONDS))
        .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
        .retryOnConnectionFailure(true)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}