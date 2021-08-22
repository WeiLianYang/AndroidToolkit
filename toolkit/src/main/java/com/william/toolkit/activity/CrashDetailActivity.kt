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
import com.william.toolkit.databinding.ActivityToolkitCrashDetailBinding
import com.william.toolkit.util.copyToClipboard
import com.william.toolkit.util.doSystemShare
import com.william.toolkit.vm.CrashDetailViewModel

/**
 * author：William
 * date：2021/6/27 09:25
 * description：崩溃详情
 */
class CrashDetailActivity : BaseActivity() {

    private val viewModel: CrashDetailViewModel by viewModels()

    override val mViewBinding: ActivityToolkitCrashDetailBinding by bindingView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        mViewBinding.includeTitle.apply {
            ivPrevious.setOnClickListener { onBackPressed() }
            tvToolTitle.setText(R.string.tool_title_crash_detail)
            ivToolRight.setImageResource(R.drawable.toolkit_share)
            ivToolRight.setOnClickListener {
                val text = mViewBinding.tvToolContent.text.toString().trim { it <= ' ' }
                copyToClipboard(this@CrashDetailActivity, text)
                doSystemShare(this@CrashDetailActivity, text)
            }
        }
    }

    private fun initData() {
        showLoading()
        viewModel.apply {
            getCrashData().observe(this@CrashDetailActivity, {
                dismissLoading()

                mViewBinding.tvToolContent.text = it
            })
        }
    }


}