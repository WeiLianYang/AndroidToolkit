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