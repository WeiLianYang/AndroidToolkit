package com.william.toolkit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.manager.DataManager
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var offset = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button1).setOnClickListener {
            ToolkitPanel.showFloating(this)
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            val perHour = 60 * 60 * 1000
            for (index in 0..19) {
                val bean = ApiRecordBean(
                    url = "https://developer.android.com/",
                    method = "POST",
                    headers = "{" +
                            "\"os\":\"android\"," +
                            "\"language\":\"en\"," +
                            "\"version\":\"1.0.0\"," +
                            "\"userAgent\":\"Google sdk_gphone_x86; Android 11\"," +
                            "\"deviceId\":\"00000000-6a5f-f86b-ffff-ffffef05ac4a\"," +
                            "\"authorization\":\"asfwerwercsdferjiui23j989jfkewiuriw\"" +
                            "}",
                    request = "{\"param1\": \"first param\",\"param2\": \"second param\"}",
                    response = "{\"code\": 200, \"success\": true, \"msg\": \"execute success\"}",
                    requestTime = System.currentTimeMillis() + offset,
                    duration = Random.nextLong(200)
                )
                DataManager.saveRecord(bean)
                offset += perHour
            }
        }
    }
}
