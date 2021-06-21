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

package com.william.toolkit.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.william.toolkit.R
import com.william.toolkit.adapter.ToolkitPanelAdapter
import com.william.toolkit.base.BaseActivity
import com.william.toolkit.base.BaseAdapter
import com.william.toolkit.base.BaseViewHolder
import com.william.toolkit.bean.ToolkitPanelBean
import com.william.toolkit.util.openActivity
import java.util.*

/**
 * @author William
 * @date 2020-02-17 16:40
 * Class Comment：工具包入口页面
 */
class ToolkitPanelActivity : BaseActivity(), View.OnClickListener {

    private var mAdapter: ToolkitPanelAdapter? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolkit_panel)
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        val mSpaceTop = findViewById<View>(R.id.tool_space_top)
        mSpaceTop.setOnClickListener(this)
        val mRecyclerView = findViewById<RecyclerView>(R.id.tool_recyclerView)
        mAdapter = ToolkitPanelAdapter(this)
        mRecyclerView.adapter = mAdapter

        mAdapter?.setOnItemChildClickListener(object :
            BaseAdapter.OnItemChildClickListener<ToolkitPanelBean> {
            override fun onItemChildCLick(
                view: View,
                holder: BaseViewHolder,
                position: Int,
                bean: ToolkitPanelBean
            ) {
                if (bean.type == ToolkitPanelBean.TYPE_RECORD) {
                    openActivity<RecordListActivity>(this@ToolkitPanelActivity)
                    finish()
                }
            }
        })

        createToolsData()
    }

    private fun createToolsData() {
        val list: ArrayList<ToolkitPanelBean> = ArrayList()
        list.add(
            ToolkitPanelBean(
                ToolkitPanelBean.TYPE_RECORD,
                getString(R.string.tool_view_record)
            )
        )
        mAdapter?.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.tool_space_top) {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    companion object {

        fun startTarget(context: Activity) {
            val intent = Intent(context, ToolkitPanelActivity::class.java)
            context.startActivity(intent)
            context.overridePendingTransition(0, 0)
        }
    }

}