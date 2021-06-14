package com.william.toolkit.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.william.toolkit.R
import com.william.toolkit.adapter.RecordListAdapter
import com.william.toolkit.base.BaseActivity
import com.william.toolkit.base.BaseAdapter
import com.william.toolkit.base.BaseViewHolder
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.util.openActivity
import com.william.toolkit.vm.RecordListViewModel

/**
 * @author William
 * @date 2020-02-17 20:26
 * Class Comment：接口数据列表
 */
class RecordListActivity : BaseActivity() {

    private val viewModel: RecordListViewModel by viewModels()

    override var loadingTextResId = R.string.loading_more
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecordListAdapter? = null
//    private var mIsAtBottom: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolkit_record_list)

        initView()

        initData()
    }

    private fun initView() {
        val mTvTitle = findViewById<TextView>(R.id.tv_toolTitle)
        mTvTitle.setText(R.string.tool_title_record_list)
        findViewById<View>(R.id.iv_previous).setOnClickListener { onBackPressed() }
        val mIvRight = findViewById<ImageView>(R.id.tv_toolRight)
        mIvRight.setImageResource(R.drawable.toolkit_clear)
        mIvRight.setOnClickListener {
            viewModel.clearRecord()
        }
        mRecyclerView = findViewById(R.id.tool_recyclerView)

    }

    private fun initData() {
        mAdapter = RecordListAdapter(this)
        mRecyclerView?.apply {
            adapter = mAdapter
            /*addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
                    layoutManager?.let {
                        mIsAtBottom =
                            it.findLastCompletelyVisibleItemPosition() == (adapter?.itemCount
                                ?: 0) - 1
                        if (mIsAtBottom) {
                            mDialog?.show()
                            viewModel.getRecordList()
                        }
                    }
                }
            })*/
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
            })

            recordListData.observe(this@RecordListActivity, {
                mDialog?.dismiss()
                mAdapter?.apply {
                    addAll(it)
                    notifyDataSetChanged()
                }
            })
            mDialog?.show()
            getRecordList()
        }
    }

}