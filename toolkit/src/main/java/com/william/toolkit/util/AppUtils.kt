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
