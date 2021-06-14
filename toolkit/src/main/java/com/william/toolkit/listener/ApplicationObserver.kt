package com.william.toolkit.listener

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.william.toolkit.ToolkitPanel
import com.william.toolkit.ext.logd
import com.william.toolkit.manager.ActStackManager

/**
 * author：William
 * date：2021/6/14 11:28
 * description：监听应用的生命周期
 */
class ApplicationObserver : LifecycleObserver {

    /**
     * ON_CREATE 在应用程序的整个生命周期中只会被调用一次
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        "Lifecycle.Event.ON_CREATE".logd()
    }

    /**
     * 应用程序出现在前台时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        "Lifecycle.Event.ON_START".logd()
    }

    /**
     * 应用程序回到前台时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        "Lifecycle.Event.ON_RESUME".logd()
        if (ToolkitPanel.hasPermission()) {
            ToolkitPanel.showFloating(ActStackManager.currentAppActivity as? FragmentActivity)
        }
    }

    /**
     * 应用程序退出到后台时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        "Lifecycle.Event.ON_PAUSE".logd()
    }

    /**
     * 应用程序退出到后台时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        "Lifecycle.Event.ON_STOP".logd()
        ToolkitPanel.removeFloating()
    }

    /**
     * 永远不会被调用到，系统不会分发调用ON_DESTROY事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        "Lifecycle.Event.ON_DESTROY".logd()
    }
}

