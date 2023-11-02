package com.example.moviediscoveryapplication.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.API_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(@ApplicationContext private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (!isInternetAvailable(context)) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        return chain.proceed(builder.build())
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}

class ModifyUrlInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val modifiedUrl = request.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val modifiedRequest = request.newBuilder().url(modifiedUrl).build()
        return chain.proceed(modifiedRequest)
    }
}

class LoggingInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("Logger", "LoggingInterceptor is intercepting the request.")
        val request = chain.request()
        val response = chain.proceed(request)
        Log.d("Logger", "Response Code: ${response.code}")
        Log.d("Logger", "Response message: ${response.message}")
        Log.d("Logger", "Cache-Control: ${response.cacheControl}")
        Log.d("Logger", "Cache Response: ${response.cacheResponse}")
        return response
    }
}

class CacheInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.DAYS)
            .build()
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}