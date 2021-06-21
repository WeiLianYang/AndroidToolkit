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

package com.william.toolkit.manager

import android.app.Activity
import java.util.*
import kotlin.collections.ArrayList

/**
 * author：William
 * date：2021/6/14 11:41
 * description：Activity堆栈管理
 */
object ActStackManager {

    /**
     * 库的activity
     */
    private var mActivityStack: Stack<Activity> = Stack()

    /**
     * 应用的activity
     */
    private var mAppActivityStack: Stack<Activity> = Stack()

    val activityCount: Int
        get() = mActivityStack.size

    val currentActivity: Activity?
        get() = if (mActivityStack.size > 0) {
            mActivityStack.peek()
        } else null

    val currentAppActivity: Activity?
        get() = if (mAppActivityStack.size > 0) {
            mAppActivityStack.peek()
        } else null

    /**
     * 入栈
     * @param activity 实例
     */
    fun addAppActivity(activity: Activity) {
        mAppActivityStack.push(activity)
    }

    /**
     * 出栈
     * @param activity 实例
     */
    fun removeAppActivity(activity: Activity) {
        mAppActivityStack.remove(activity)
    }

    /**
     * 入栈
     * @param activity 实例
     */
    fun addActivity(activity: Activity) {
        mActivityStack.push(activity)
    }

    /**
     * 出栈
     * @param activity 实例
     */
    fun removeActivity(activity: Activity) {
        mActivityStack.remove(activity)
    }

    /**
     * 彻底退出
     */
    fun finishAllActivity() {
        var activity: Activity?
        while (!mActivityStack.empty()) {
            activity = mActivityStack.pop()
            activity?.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    @Suppress("unused")
    fun finishActivity(cls: Class<*>) {
        for (activity in mActivityStack) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    /**
     * 查找栈中是否存在指定的activity
     * @param cls class
     */
    fun checkActivity(cls: Class<*>): Boolean {
        for (activity in mActivityStack) {
            if (activity.javaClass == cls) {
                return true
            }
        }
        return false
    }

    /**
     * 结束指定的Activity
     *  @param activity 实例
     */
    fun finishActivity(activity: Activity?) {
        activity?.let {
            mActivityStack.remove(it)
            it.finish()
        }
    }

    /**
     * finish指定的activity之上所有的activity
     */
    @Suppress("unused")
    fun finishToActivity(actCls: Class<*>, isIncludeSelf: Boolean): Boolean {
        val buf: MutableList<Activity> = ArrayList()
        val size = mActivityStack.size
        var activity: Activity
        for (i in size - 1 downTo 0) {
            activity = mActivityStack[i]
            if (activity.javaClass.isAssignableFrom(actCls)) {
                for (a in buf) {
                    a.finish()
                }
                return true
            } else if (i == size - 1 && isIncludeSelf) {
                buf.add(activity)
            } else if (i != size - 1) {
                buf.add(activity)
            }
        }
        return false
    }

    /**
     * finish指定的activity之上所有的activity
     */
    @SafeVarargs
    @Suppress("unused")
    fun finishOtherActivity(vararg actCls: Class<*>) {
        try {
            val buf: MutableList<Activity> = ArrayList()
            val size = mActivityStack.size
            for (i in size - 1 downTo 0) {
                val activity = mActivityStack[i]
                for (actCl in actCls) {
                    if (activity.javaClass != actCl) {
                        buf.add(activity)
                    }
                }
            }
            for (activity in buf) {
                finishActivity(activity)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}