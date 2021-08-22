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

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.william.toolkit.bean.ApiRecordBean
import java.util.regex.Pattern

/**
 * @author William
 * @date 2020-02-18 11:45
 * Class Comment：JSON格式化工具
 */

/**
 * 匹配Key值的正则表达式
 */
private const val PROPERTY_REGEX = "(\\\"(.*?)\\\":)"

/**
 * JSON 语法着色
 *
 * @param jsonStr 格式化后的JSON字符串
 * @return coloring json
 */
fun coloringToJson(jsonStr: String): SpannableString {
    val spannableString = SpannableString(jsonStr)

    // 给key值设置 绿色
    coloringJson(spannableString, jsonStr, PROPERTY_REGEX, "#009933", 0, -1)

    // 给指定的标题设置 玫红色
    for (title in ApiRecordBean.TITLE_ARRAY) {
        coloringJson(spannableString, jsonStr, title, "#990000", 0, 0)
    }
    return spannableString
}

/**
 * 设置富文本
 *
 * @param spannableString 目标富文本
 * @param jsonStr         源文本
 * @param regex           表达式
 * @param colorStr        颜色
 * @param startOffset     匹配到开始位置的索引偏移量
 * @param endOffset       匹配到结束的索引偏移量
 */
@Suppress("SameParameterValue")
fun coloringJson(
    spannableString: SpannableString, jsonStr: String,
    regex: String, colorStr: String, startOffset: Int, endOffset: Int
) {
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(jsonStr)
    while (matcher.find()) {
        val colorSpan = ForegroundColorSpan(Color.parseColor(colorStr))
        spannableString.setSpan(
            colorSpan,
            matcher.start() + startOffset,
            matcher.end() + endOffset,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
}

/**
 * 对json字符串进行格式化
 *
 * @param content        要格式化的json字符串
 * @param indent         是否进行缩进(false时压缩为一行)
 * @param colonWithSpace key冒号后是否加入空格
 * @return json string format
 */
@JvmOverloads
fun format(content: String?, indent: Boolean = true, colonWithSpace: Boolean = true): String? {
    if (content == null) return null
    val sb = StringBuilder()
    var count = 0
    var inString = false
    val tab = "\t"
    for (i in content.indices) {
        when (val ch = content[i]) {
            '{', '[' -> {
                sb.append(ch)
                if (!inString) {
                    if (indent) {
                        sb.append("\n")
                        count++
                        var j = 0
                        while (j < count) {
                            sb.append(tab)
                            j++
                        }
                    }
                }
            }
            '\uFEFF' -> if (inString) sb.append(ch)
            '}', ']' -> if (!inString) {
                if (indent) {
                    count--
                    sb.append("\n")
                    var j = 0
                    while (j < count) {
                        sb.append(tab)
                        j++
                    }
                }
                sb.append(ch)
            } else {
                sb.append(ch)
            }
            ',' -> {
                sb.append(ch)
                if (!inString) {
                    if (indent) {
                        sb.append("\n")
                        var j = 0
                        while (j < count) {
                            sb.append(tab)
                            j++
                        }
                    } else {
                        if (colonWithSpace) {
                            sb.append(' ')
                        }
                    }
                }
            }
            ':' -> {
                sb.append(ch)
                if (!inString) {
                    if (colonWithSpace) {  //key名称冒号后加空格
                        sb.append(' ')
                    }
                }
            }
            ' ', '\n', '\t' -> if (inString) {
                sb.append(ch)
            }
            '"' -> {
                if (i > 0 && content[i - 1] != '\\') {
                    inString = !inString
                }
                sb.append(ch)
            }
            else -> sb.append(ch)
        }
    }
    return sb.toString()
}
