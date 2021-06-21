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