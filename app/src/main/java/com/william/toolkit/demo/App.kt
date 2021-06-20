package com.william.toolkit.demo

import android.app.Application
import com.william.toolkit.ToolkitPanel
import com.william.toolkit.bean.ToolkitConfig

/**
 * author：William
 * date：2021/6/14 11:23
 * description：
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // set config data
        val config = ToolkitConfig.Builder()
            .setDebugMode(BuildConfig.DEBUG)
            .setSuccessCode(0) // define the success code
            .setSuccessCodeKey("errorCode") // define the success code key name
//            .setSuccessCodeKey("code")
            .build()
        // init toolkit
        ToolkitPanel.init(this, config)
    }

}