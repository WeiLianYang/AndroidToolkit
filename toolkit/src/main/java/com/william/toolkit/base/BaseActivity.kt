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

package com.william.toolkit.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.william.toolkit.R
import com.william.toolkit.manager.ActStackManager

/**
 * @author William
 * @date 2020-02-18 15:44
 * Class Comment：
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var mDialog: Dialog? = null
    protected open var loadingTextResId: Int = R.string.loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActStackManager.addActivity(this)

        createLoadingDialog()
    }

    override fun onDestroy() {
        mDialog?.dismiss()
        ActStackManager.removeActivity(this)
        super.onDestroy()
    }

    @SuppressLint("InflateParams")
    private fun createLoadingDialog() {
        AlertDialog.Builder(this, R.style.tool_theme_dialog).apply {
            val view = layoutInflater.inflate(R.layout.dialog_toolkit_loading, null)
            view.findViewById<TextView>(R.id.tv_loading).text = getString(loadingTextResId)
            setView(view)
            setCancelable(true)
            setFinishOnTouchOutside(false)
            mDialog = create()
        }
    }

}