package com.william.toolkit.listener

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.william.toolkit.ext.logd
import com.william.toolkit.manager.ActStackManager


/**
 * @author William
 * @date 2021/6/14 11:42
 * Class Comment：全局activity生命周期监听
 */
class ActLifeCircleCallback : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        "onActivityCreated: ${activity.javaClass.simpleName}".logd()
        ActStackManager.addAppActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        "onActivityStarted: ${activity.javaClass.simpleName}".logd()
    }

    override fun onActivityResumed(activity: Activity) {
        "onActivityResumed: ${activity.javaClass.simpleName}".logd()
    }

    override fun onActivityPaused(activity: Activity) {
        "onActivityPaused: ${activity.javaClass.simpleName}".logd()
    }

    override fun onActivityStopped(activity: Activity) {
        "onActivityStopped: ${activity.javaClass.simpleName}".logd()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        "onActivitySaveInstanceState: ${activity.javaClass.simpleName}".logd()
    }

    override fun onActivityDestroyed(activity: Activity) {
        "onActivityDestroyed: ${activity.javaClass.simpleName}".logd()
        ActStackManager.removeAppActivity(activity)
    }

}