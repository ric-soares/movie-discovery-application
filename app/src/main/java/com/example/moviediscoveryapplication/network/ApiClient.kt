package com.example.moviediscoveryapplication.network

import android.util.Log
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.API_KEY
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.BASE_URL
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.TAG
import okhttp3.ConnectionPool
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor() {

    // interceptor: monitor, modify and retry api calls.
    // An Interceptor is a mechanism in Retrofit that allows you to intercept and manipulate network requests and responses.

    private val requestInterceptor = Interceptor { chain ->
        // builds a modified URL by adding the api_key as a query parameter.
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        // creates a new request using the modified URL.
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        // the intercepted request is allowed to continue its normal execution with the modifications made by the interceptor.
        return@Interceptor chain.proceed(request)
    }

    // logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d(
            TAG,
            "http log: $message"
        )
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // the builder configures the client with the requestInterceptor and loggingInterceptor defined above, sets a connection timeout of 60 seconds
    // and sets a connection pool to reuse the same connection for a max of 5 idle connections.
    // also it's configured to retry a request automatically when for some reason the connection fails.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)

        // allows the client to reuse existing connections for multiple requests instead of creating a new one each time a request is sent.
        .connectionPool(ConnectionPool(5, 500, TimeUnit.MILLISECONDS))
        .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
        .retryOnConnectionFailure(true)

        .build()

    // represents a configured instance of Retrofit by setting the base url, the client and the deserializer.
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}