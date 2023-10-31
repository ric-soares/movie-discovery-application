package com.example.moviediscoveryapplication.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.moviediscoveryapplication.common.exceptions.NetworkExceptions
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.API_KEY
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants.TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(@ApplicationContext private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable(context)) {
            throw NetworkExceptions.noConnectivityException()
        }

        return chain.proceed(chain.request())
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
        val request = chain.request()
        val response = chain.proceed(request)
        Log.d(TAG, "http log: ${response.message}")
        return response
    }
}
