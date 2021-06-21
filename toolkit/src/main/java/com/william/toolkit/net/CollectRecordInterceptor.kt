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

package com.william.toolkit.net

import com.william.toolkit.ToolkitPanel
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.manager.DataManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


/**
 * author：William
 * date：2021/6/15 07:09
 * description：通用的收集日志数据拦截器
 */
class CollectRecordInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val response = chain.proceed(request)

        collectApiRecordData(request, response)

        return response
    }

    @Throws(IOException::class)
    private fun collectApiRecordData(request: Request, response: Response) {
        if (ToolkitPanel.isDebugMode) {
            val utf8 = StandardCharsets.UTF_8
            val requestBody = request.body
            var requestBodyString: String? = null
            requestBody?.let {
                val buffer = Buffer()
                it.writeTo(buffer)
                val charset: Charset?
                val contentType = it.contentType()
                charset = contentType?.charset(utf8)
                requestBodyString = charset?.let { charsetSelf -> buffer.readString(charsetSelf) }
            }
            val startNanoTime = System.nanoTime()
            val requestTime = System.currentTimeMillis()
            val duration = TimeUnit.NANOSECONDS.toMillis(System.currentTimeMillis() - startNanoTime)
            val responseBody = response.body
            responseBody?.let {
                val source = it.source()
                source.request(Long.MAX_VALUE)
                val responseBodyString = source.buffer.clone().readString(utf8)
                val headers = request.headers
                val headerJson = JSONObject()
                val iterator = headers.iterator()
                while (iterator.hasNext()) {
                    val (first, second) = iterator.next()
                    try {
                        headerJson.put(first, second)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                val bean = ApiRecordBean(
                    url = request.url.toString(),
                    method = request.method,
                    headers = headerJson.toString(),
                    request = requestBodyString,
                    response = responseBodyString,
                    requestTime = requestTime,
                    duration = duration
                )
                DataManager.saveRecord(bean)
            }
        }
    }
}