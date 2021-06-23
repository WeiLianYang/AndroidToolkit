/*
 * Copyright WeiLianYang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.william.toolkit.demo.service

import com.william.toolkit.BuildConfig
import com.william.toolkit.net.ApiRecordInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * author：William
 * date：2021/6/20 21:52
 * description：
 */
private const val TIMEOUT: Long = 15

private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .client(getOkHttpClient())
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun getOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient().newBuilder()
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    builder.run {
        addInterceptor(httpLoggingInterceptor)
        addInterceptor(HeaderInterceptor())

        // Add the interceptor defined in the package
        addInterceptor(ApiRecordInterceptor())

        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    }
    return builder.build()
}

fun <T> createService(clazz: Class<T>): T {
    return getRetrofit().create(clazz)
}

inline fun <reified T> createService(): T = createService(T::class.java)