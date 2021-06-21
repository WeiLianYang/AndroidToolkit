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

package com.william.toolkit.ext

import androidx.lifecycle.viewModelScope
import com.william.toolkit.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 调用携程
 * @param block 执行耗时任务
 * @param success 成功回调（在主线程）
 * @param error 失败回调（在主线程）
 */
fun <T> BaseViewModel.launch(
    block: suspend () -> T,
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        kotlin.runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}

/**
 * 调用携程
 * @param block 执行网络请求任务
 * @param success 成功回调（在主线程）
 * @param error 失败回调（在主线程）
 */
fun <T> BaseViewModel.request(
    block: suspend () -> T,
    success: (T) -> Unit = {},
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        kotlin.runCatching {
            block()
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}