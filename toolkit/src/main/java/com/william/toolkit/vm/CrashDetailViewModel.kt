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

package com.william.toolkit.vm

import android.text.SpannableString
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.william.toolkit.base.BaseViewModel
import com.william.toolkit.bean.AppCrashBean
import com.william.toolkit.manager.DataManager
import com.william.toolkit.util.coloringJson
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*


/**
 * author：William
 * date：2021/6/27 09:29
 * description：Crash Detail ViewModel
 */
class CrashDetailViewModel : BaseViewModel() {

    private val crashDataFlow = DataManager.getCrashInfo()

    fun getCrashData(): LiveData<SpannableString> {
        return crashDataFlow.map { bean ->
            val time =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(bean?.time)
            val sb = StringBuilder()
                .append("${AppCrashBean.TITLE_ARRAY[0]} : $time")
                .append("\n").append("\n")
                .append("${AppCrashBean.TITLE_ARRAY[1]} : ${bean?.threadName}")
                .append("\n").append("\n")
                .append("${AppCrashBean.TITLE_ARRAY[2]} : ${bean?.cause}")
                .append("\n").append("\n")
                .append(bean?.message)

            val source = "$sb"

            val spannableString = SpannableString(source)
            for (title in AppCrashBean.TITLE_ARRAY) {
                coloringJson(spannableString, source, title, "#ff0000", 0, 0)
            }
            spannableString
        }.asLiveData()
    }

}