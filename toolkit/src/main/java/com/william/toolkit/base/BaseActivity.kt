package com.william.toolkit.base

import android.annotation.SuppressLint
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
        mDialog = Dialog(this, R.style.tool_theme_dialog).apply {
            val view = layoutInflater.inflate(R.layout.dialog_loading, null)
            view.findViewById<TextView>(R.id.tv_loading).text = getString(loadingTextResId)
            setContentView(view)
            setCancelable(true)
            setCanceledOnTouchOutside(false)
        }
    }

}