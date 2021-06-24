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
import java.util.concurrent.TimeUnit


/**
 * author：William
 * date：2021/6/15 07:09
 * description：通用的收集日志数据拦截器
 */
class ApiRecordInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val startNanoTime = System.nanoTime()
        val requestTime = System.currentTimeMillis()
        var duration: Long = 0
        var response: Response? = null

        kotlin.runCatching {
            response = chain.proceed(request)

            duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanoTime)

            response!!
        }.onSuccess {
            collectApiRecordData(request, it, requestTime, duration)
        }.onFailure {
            collectApiRecordData(request, null, requestTime, duration, it.message)
            throw it
        }
        return response!!
    }

    @Throws(IOException::class)
    private fun collectApiRecordData(
        request: Request,
        response: Response?,
        requestTime: Long,
        duration: Long,
        errorMsg: String? = null
    ) {
        if (ToolkitPanel.isDebugMode) {
            val utf8 = Charset.forName("UTF-8")
            var requestBody: String? = null
            request.body()?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                requestBody = body.contentType()
                    ?.charset(utf8)
                    ?.let { buffer.readString(it) }
            }

            var responseBody: String? = null
            response?.body()?.let {
                val source = it.source()
                source.request(Long.MAX_VALUE)
                responseBody = source.buffer().clone().readString(utf8)
            }

            val headers = request.headers()
            val headerJson = JSONObject()
            val iterator = headers.names().iterator()
            while (iterator.hasNext()) {
                val name = iterator.next()
                val values = headers.values(name)
                val value = if (values.size == 1) values.first() else values.toString()
                try {
                    headerJson.put(name, value)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val bean = ApiRecordBean(
                url = request.url().toString(),
                method = request.method(),
                headers = headerJson.toString(),
                request = requestBody,
                response = responseBody,
                requestTime = requestTime,
                duration = duration,
                httpCode = response?.code() ?: -1,
                errorMsg = errorMsg
            )
            DataManager.saveRecord(bean)
        }
    }
}