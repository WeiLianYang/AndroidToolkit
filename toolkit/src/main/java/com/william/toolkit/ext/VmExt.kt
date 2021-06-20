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