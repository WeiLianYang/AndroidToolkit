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

package com.william.toolkit.demo

import android.app.Application
import com.william.toolkit.ToolkitPanel
import com.william.toolkit.bean.ToolkitConfig
import com.william.toolkit.manager.DataManager

/**
 * author：William
 * date：2021/6/14 11:23
 * description：
 */
class App : Application(), Thread.UncaughtExceptionHandler {

    override fun onCreate() {
        super.onCreate()
        // set config data
        val config = ToolkitConfig.Builder().setDebugMode(BuildConfig.DEBUG).build()
        // init toolkit
        ToolkitPanel.init(this, config)

        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        DataManager.saveCrash(t, e)
    }
}