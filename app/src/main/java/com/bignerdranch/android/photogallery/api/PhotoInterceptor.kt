package com.bignerdranch.android.photogallery.api

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "d3134c974bb7b21aa83e708262657641"
private const val TAG = "Interceptor"

class PhotoInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        //Log.i(TAG, "originalRequest = $originalRequest")
        val originalUrl = originalRequest.url().toString()

        Log.i(TAG, "originalUrl = $originalUrl")
        if (originalUrl.startsWith("https://live")) {
            return chain.proceed(chain.request())
        }
        val newUrl: HttpUrl = originalRequest.url().newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("extras", "url_s")
            .addQueryParameter("safesearch", "1")
            .addQueryParameter("license", "7")
            .addQueryParameter("sort", "interestingness-desc")
            .build()

        Log.i(TAG, "newUrl = $newUrl")

        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        Log.i(TAG, "newRequest = $newRequest")

        val result = chain.proceed(newRequest)
        Log.i(TAG, "newResponse = $result")
        Log.i(
            TAG, "==============================================================" +
                    "" +
                    "" +
                    "" +
                    "==========================================================================="
        )



        return result
    }
}

