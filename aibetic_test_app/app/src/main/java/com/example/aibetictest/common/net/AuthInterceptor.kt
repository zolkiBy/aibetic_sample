package com.example.aibetictest.common.net

import com.example.aibetictest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val key = BuildConfig.API_KEY
        val request = chain.request()
        val initialUrl = request.url

        val requestUrl = if (key.isNotBlank()) {
            initialUrl.newBuilder()
                .addQueryParameter("app_id", key)
                .build()
        } else {
            initialUrl
        }
        val requestWithApiKey = request.newBuilder()
            .url(requestUrl)
            .build()

        return chain.proceed(requestWithApiKey)
    }
}
