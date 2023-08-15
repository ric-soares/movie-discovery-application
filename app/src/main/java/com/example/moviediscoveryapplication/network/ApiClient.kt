package com.example.moviediscoveryapplication.network

import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.API_KEY
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private lateinit var retrofit: Retrofit

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

    // the builder configures the client with the requestInterceptor that was defined above and sets a connection timeout of 60 seconds.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    // returns a configured instance of Retrofit by setting the base url, the client and the deserializer.
    fun getClient(): Retrofit {
        retrofit.newBuilder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}

// interceptor: monitor, modify and retry api calls.
// An Interceptor is a mechanism in Retrofit that allows you to intercept and manipulate network requests and responses.