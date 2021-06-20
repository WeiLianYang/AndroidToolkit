package com.william.toolkit.demo.service

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * author：William
 * date：2021/6/20 21:33
 * description：
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val builder = request.newBuilder().apply {
            addHeader("authorization", "111werwercsdferjiui23j989jfkewiuriw")
            addHeader("language", "en")
            addHeader("os", "android")
            addHeader("version", "1.0.1")
            addHeader("deviceid", "00000000-1111-f86b-ffff-ffffef05ac4a")
            val userAgent = "Google sdk_gphone_x86; Android 12"
            addHeader("userAgent", userAgent)
        }

        return chain.proceed(builder.build())
    }
}