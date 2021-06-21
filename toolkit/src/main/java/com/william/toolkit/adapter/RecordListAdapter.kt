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

package com.william.toolkit.adapter

import android.app.Activity
import com.william.toolkit.R
import com.william.toolkit.ToolkitPanel
import com.william.toolkit.base.BaseAdapter
import com.william.toolkit.base.BaseViewHolder
import com.william.toolkit.bean.ApiRecordBean
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author William
 * @date 2020-02-17 20:49
 * Class Comment：接口数据列表适配器
 */
class RecordListAdapter(activity: Activity) : BaseAdapter<ApiRecordBean>(activity) {

    override fun getLayoutResourceId() = R.layout.item_toolkit_record

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, bean: ApiRecordBean) {
        var response: JSONObject? = null
        try {
            response = JSONObject(bean.response ?: "")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val text = if (response != null) {
            val code = response.optInt(ToolkitPanel.successCodeKey)
            if (code == ToolkitPanel.successCode) {
                "✅ "
            } else {
                "❌ "
            }
        } else {
            "❌ "
        }
        holder.setText(R.id.tv_tool_icon, text)
        holder.setText(R.id.tv_tool_url, bean.url)
        val time = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        ).format(Date(bean.requestTime))
        holder.setText(R.id.tv_tool_time, time)
    }

}