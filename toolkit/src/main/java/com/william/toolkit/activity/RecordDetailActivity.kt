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

import android.os.Bundle
import androidx.activity.viewModels
import com.william.toolkit.R
import com.william.toolkit.base.BaseActivity
import com.william.toolkit.databinding.ActivityToolkitRecordDetailBinding
import com.william.toolkit.util.copyToClipboard
import com.william.toolkit.util.doSystemShare
import com.william.toolkit.vm.RecordDetailViewModel

/**
 * @author William
 * @date 2020-02-17 20:26
 * Class Comment：接口数据详情
 */
class RecordDetailActivity : BaseActivity() {

    private val viewModel: RecordDetailViewModel by viewModels()

    override val mViewBinding: ActivityToolkitRecordDetailBinding by bindingView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        mViewBinding.includeTitle.apply {
            ivPrevious.setOnClickListener { onBackPressed() }
            tvToolTitle.setText(R.string.tool_title_record_detail)
            ivToolRight.setImageResource(R.drawable.toolkit_share)
            ivToolRight.setOnClickListener {
                val text = mViewBinding.tvToolContent.text.toString().trim { it <= ' ' }
                copyToClipboard(this@RecordDetailActivity, text)
                doSystemShare(this@RecordDetailActivity, text)
            }
        }
    }

    private fun initData() {
        val id: Long = intent.getLongExtra("id", -1)
        showLoading()
        viewModel.getDetailData(id).observe(this, {
            dismissLoading()
            mViewBinding.tvToolContent.text = it
        })
    }


}