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

import android.util.Log
import androidx.core.provider.FontRequest
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.EmojiCompat.InitCallback
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.william.toolkit.R
import com.william.toolkit.ToolkitPanel

/**
 * author：William
 * date：2021/6/21 22:53
 * description：https://github.com/googlearchive/android-EmojiCompat
 */
object EmojiConfigHelper {
    private const val TAG = "EmojiConfigHelper"

    /**
     * Change this to `false` when you want to use the downloadable Emoji font.
     */
    private const val USE_BUNDLED_EMOJI = true
    fun initEmojiCompat() {
        val config: EmojiCompat.Config = if (USE_BUNDLED_EMOJI) {
            // Use the bundled font for EmojiCompat
            BundledEmojiCompatConfig(ToolkitPanel.appContext)
        } else {
            // Use a downloadable font for EmojiCompat
            val fontRequest = FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs
            )
            FontRequestEmojiCompatConfig(ToolkitPanel.appContext, fontRequest)
        }
        config.setReplaceAll(true)
            .registerInitCallback(object : InitCallback() {
                override fun onInitialized() {
                    Log.i(TAG, "EmojiCompat initialized")
                }

                override fun onFailed(throwable: Throwable?) {
                    Log.e(TAG, "EmojiCompat initialization failed", throwable)
                }
            })
        EmojiCompat.init(config)
    }
}