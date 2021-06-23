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

package com.william.toolkit

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.*
import android.view.View.OnTouchListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ProcessLifecycleOwner
import com.william.toolkit.activity.ToolkitPanelActivity
import com.william.toolkit.bean.ToolkitConfig
import com.william.toolkit.ext.logd
import com.william.toolkit.ext.logi
import com.william.toolkit.ext.logw
import com.william.toolkit.fragment.PermissionFragment
import com.william.toolkit.listener.ActLifeCircleCallback
import com.william.toolkit.listener.ApplicationObserver
import com.william.toolkit.manager.ActStackManager
import com.william.toolkit.util.EmojiConfigHelper

/**
 * @author William
 * @date 2020-02-18 13:02
 * Class Comment：工具包面板管理类
 */
@SuppressLint("StaticFieldLeak")
object ToolkitPanel {

    private var isFloatingShowing = false
    private var displayView: View? = null

    lateinit var appContext: Context
    var activity: FragmentActivity? = null

    private var windowParams: WindowManager.LayoutParams? = null
    private var windowManager: WindowManager? = null

    private val isPageShowing: Boolean
        get() = ActStackManager.activityCount > 0

    var isDebugMode: Boolean = BuildConfig.DEBUG

    init {
        windowParams = WindowManager.LayoutParams().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                type = WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            gravity = Gravity.TOP or Gravity.START
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            x = 0
            y = 800
        }
    }

    @JvmStatic
    fun init(context: Application, config: ToolkitConfig) {
        appContext = context
        isDebugMode = config.debugMode
        context.registerActivityLifecycleCallbacks(ActLifeCircleCallback())
        ProcessLifecycleOwner.get().lifecycle.addObserver(ApplicationObserver())
        EmojiConfigHelper.initEmojiCompat()
    }

    @SuppressLint("InflateParams")
    @JvmStatic
    fun showFloating(activity: FragmentActivity? = null, fragment: Fragment? = null) {
        "showFloating params activity: ${activity?.javaClass?.simpleName}, fragment: ${fragment?.javaClass?.simpleName}".logd()
        (fragment ?: activity) ?: return
        if (!checkPermission(activity, fragment)) {
            return
        }
        if (isFloatingShowing) {
            "已显示工具面板悬浮入口".logw()
            return
        }
        isFloatingShowing = true

        if (windowManager == null) {
            windowManager = appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        displayView =
            LayoutInflater.from(appContext).inflate(R.layout.floating_toolkit, null)
        displayView?.apply {
            setOnTouchListener(FloatingTouchListener())
            setOnClickListener {
                if (isPageShowing) {
                    ActStackManager.finishAllActivity()
                } else {
                    ActStackManager.currentAppActivity?.let { context ->
                        ToolkitPanelActivity.startTarget(context)
                    }
                }
            }
            windowManager?.addView(this, windowParams)
        }
    }

    /**
     * 移除悬浮窗
     */
    @JvmStatic
    fun removeFloating() {
        "移除工具面板悬浮入口".logd()
        windowManager?.removeView(displayView)
        isFloatingShowing = false
    }

    private class FloatingTouchListener : OnTouchListener {
        private var x = 0
        private var y = 0

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX.toInt()
                    val newY = event.rawY.toInt()
                    val movedX = newX - x
                    val movedY = newY - y
                    x = newX
                    y = newY
                    windowParams?.x = windowParams?.x?.plus(movedX)
                    windowParams?.y = windowParams?.y?.plus(movedY)
                    windowManager?.updateViewLayout(view, windowParams)
                }
                else -> {
                }
            }
            return false
        }
    }

    fun hasPermission(): Boolean {
        return !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(appContext))
    }

    private fun checkPermission(activity: FragmentActivity?, fragment: Fragment?): Boolean {
        return if (!hasPermission()) {
            "没有权限，去申请".logw()
            PermissionFragment.createInstance(activity, fragment)?.requestPermission()
            false
        } else {
            "已有权限".logi()
            true
        }
    }

}