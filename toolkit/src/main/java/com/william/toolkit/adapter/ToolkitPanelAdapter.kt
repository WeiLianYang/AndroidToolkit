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