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

package com.william.toolkit.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * 打开某个activity，传值，也可不传值
 * @param context 上下文
 * @param block 传参表达式
 * @param T 目标activity
 */
inline fun <reified T> openActivity(
    context: Context,
    noinline block: (Intent.() -> Unit) = {}
) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

/**
 * 复制数据到剪切板
 *
 * @param context 上下文
 * @param text    要复制的目标文本
 */
fun copyToClipboard(context: Context, text: String?) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("toolkit_record_text", text)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show()
}

/**
 * 启动系统分享
 *
 * @param context context
 * @param text    分享的文本
 */
fun doSystemShare(context: Context, text: String?) {
    val textIntent = Intent(Intent.ACTION_SEND)
    textIntent.type = "text/plain"
    textIntent.putExtra(Intent.EXTRA_TEXT, text)
    context.startActivity(Intent.createChooser(textIntent, "api数据"))
}
