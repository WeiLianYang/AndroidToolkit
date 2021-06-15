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
            // 收集单条数据
            DataManager.saveRecord(createRecord())
        }

        findViewById<View>(R.id.button3).setOnClickListener {
            // 收集多条数据
            val perHour = 60 * 60 * 1000
            val list = arrayListOf<ApiRecordBean>()
            for (index in 0..19) {
                offset += perHour
                list.add(createRecord(index))
            }
            DataManager.saveRecordList(list)
        }
    }

    private fun createRecord(index: Int = 0): ApiRecordBean {
        val even = index % 2 == 0
        val code = if (even) 200 else 500
        val success = code == 200
        val msg = if (success) "execute success" else "execute failed"

        return ApiRecordBean(
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
            response = "{\"code\": $code, \"success\": $success, \"msg\": \"$msg\"}",
            requestTime = System.currentTimeMillis() + offset,
            duration = Random.nextLong(200)
        )
    }
}
