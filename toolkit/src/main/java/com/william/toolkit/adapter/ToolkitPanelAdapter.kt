package com.william.toolkit.adapter

import android.app.Activity
import com.william.toolkit.R
import com.william.toolkit.base.BaseAdapter
import com.william.toolkit.base.BaseViewHolder
import com.william.toolkit.bean.ToolkitPanelBean

/**
 * @author William
 * @date 2020-02-17 18:54
 * Class Comment：工具包入口适配器
 */
class ToolkitPanelAdapter(activity: Activity) : BaseAdapter<ToolkitPanelBean>(activity) {

    override fun getLayoutResourceId() = R.layout.item_toolkit_label

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, bean: ToolkitPanelBean) {
        holder.setText(R.id.tool_button, bean.name)
        addOnChildClickListener(R.id.tool_button, holder, position, bean)
    }
}