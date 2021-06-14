package com.william.toolkit.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.william.toolkit.R
import com.william.toolkit.base.BaseActivity
import com.william.toolkit.bean.ApiRecordBean
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

    private lateinit var mTvContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolkit_record_detail)

        initView()

        initData()
    }

    private fun initView() {
        val mTvTitle = findViewById<TextView>(R.id.tv_toolTitle)
        val mIvRight = findViewById<ImageView>(R.id.tv_toolRight)
        mTvContent = findViewById(R.id.tv_toolContent)

        mTvTitle.setText(R.string.tool_title_record_detail)
        mIvRight.setImageResource(R.drawable.toolkit_share)

        findViewById<View>(R.id.iv_previous).setOnClickListener { onBackPressed() }

        mIvRight.setOnClickListener {
            val text = mTvContent.text.toString().trim { it <= ' ' }
            copyToClipboard(this, text)
            doSystemShare(this, text)
        }
    }

    private fun initData() {
        val bean: ApiRecordBean? = intent.getParcelableExtra("bean")

        mDialog?.show()
        viewModel.apply {
            recordData.observe(this@RecordDetailActivity, {
                mDialog?.dismiss()
                mTvContent.text = it
            })

            handleData(bean)
        }
    }


}