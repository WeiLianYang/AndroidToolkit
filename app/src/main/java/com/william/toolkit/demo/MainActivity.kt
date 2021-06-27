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

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.william.toolkit.ToolkitPanel
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.demo.databinding.ActivityMainBinding
import com.william.toolkit.demo.vm.MainViewModel
import com.william.toolkit.manager.DataManager
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var offset = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            ToolkitPanel.showFloating(this)
        }

        binding.button2.setOnClickListener {
            viewModel.testApi()
        }

        binding.button3.setOnClickListener {
            // 收集单条数据
            DataManager.saveRecord(createRecord())
        }

        binding.button4.setOnClickListener {
            // 收集多条数据
            val perHour = 60 * 60 * 1000
            val list = arrayListOf<ApiRecordBean>()
            for (index in 0..19) {
                offset += perHour
                list.add(createRecord(index))
            }
            DataManager.saveRecordList(list)
        }

        binding.button5.setOnClickListener {
            throw NullPointerException()
        }

        viewModel.bannerMsg.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
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
            duration = Random.nextLong(200),
            httpCode = code
        )
    }
}
