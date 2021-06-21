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

package com.william.toolkit.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.william.toolkit.ext.loge
import com.william.toolkit.ext.logi

/**
 * author：William
 * date：2021/6/14 09:49
 * description：用于请求权限的Fragment
 */
class PermissionFragment : Fragment() {

    fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context?.packageName}")
            )
            startActivityForResult(intent, OVERLAY_PERMISSION)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OVERLAY_PERMISSION -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(context)) {
                        "权限授予成功".logi()
                    } else {
                        "权限未授予".loge()
                    }
                }
            }
        }
    }

    companion object {

        private const val TAG = "PermissionFragment"
        const val OVERLAY_PERMISSION = 1

        fun createInstance(
            activity: FragmentActivity?,
            fragment: Fragment?
        ): PermissionFragment? {
            val fragmentManager = fragment?.childFragmentManager ?: activity?.supportFragmentManager
            var instance = fragmentManager?.findFragmentByTag(TAG)
            return if (instance != null) {
                instance as? PermissionFragment
            } else {
                instance = PermissionFragment()
                fragmentManager?.beginTransaction()?.add(instance, TAG)
                    ?.commitNowAllowingStateLoss()
                instance
            }
        }
    }
}