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
import android.view.View
import androidx.activity.viewModels
import com.william.toolkit.R
import com.william.toolkit.adapter.RecordListAdapter
import com.william.toolkit.base.BaseActivity
import com.william.toolkit.base.BaseAdapter
import com.william.toolkit.base.BaseViewHolder
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.databinding.ActivityToolkitRecordListBinding
import com.william.toolkit.util.openActivity
import com.william.toolkit.vm.RecordListViewModel

/**
 * @author William
 * @date 2020-02-17 20:26
 * Class Comment：接口数据列表
 */
class RecordListActivity : BaseActivity() {

    private val viewModel: RecordListViewModel by viewModels()

    override val mViewBinding: ActivityToolkitRecordListBinding by bindingView()

    override var loadingTextResId = R.string.loading

    private var mAdapter: RecordListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        mViewBinding.includeTitle.apply {
            ivPrevious.setOnClickListener { onBackPressed() }
            tvToolTitle.setText(R.string.tool_title_record_list)
            ivToolRight.setImageResource(R.drawable.toolkit_clear)
            ivToolRight.setOnClickListener {
                viewModel.clearRecord()
            }
        }
    }

    private fun initData() {
        mAdapter = RecordListAdapter(this)
        mViewBinding.recyclerView.apply {
            adapter = mAdapter
        }
        mAdapter?.apply {
            setOnItemClickListener(object :
                BaseAdapter.OnItemClickListener<ApiRecordBean> {
                override fun onItemClick(
                    holder: BaseViewHolder,
                    position: Int,
                    bean: ApiRecordBean?
                ) {
                    openActivity<RecordDetailActivity>(this@RecordListActivity) {
                        putExtra("bean", bean)
                    }
                }
            })
        }

        viewModel.apply {
            clearFlag.observe(this@RecordListActivity, {
                mAdapter?.apply {
                    clear()
                    notifyDataSetChanged()
                }
                mViewBinding.tvEmptyData.visibility = View.VISIBLE
            })

            recordListData.observe(this@RecordListActivity, {
                dismissLoading()
                mAdapter?.apply {
                    addAll(it)
                    notifyDataSetChanged()
                }
                mViewBinding.tvEmptyData.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            })
            showLoading()
            getRecordList()
        }
    }

}