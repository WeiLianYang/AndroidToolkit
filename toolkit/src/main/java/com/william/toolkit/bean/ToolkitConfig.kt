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

package com.william.toolkit.bean

import com.william.toolkit.BuildConfig

/**
 * author：William
 * date：2021/6/14 21:29
 * description：配置数据
 */
class ToolkitConfig(builder: Builder) {

    /**
     * 标识网络请求成功的业务码字段名
     */
    var successCodeKey: String = builder.successCodeKey

    /**
     * 标识网络请求成功的业务码
     */
    var successCode: Int = builder.successCode

    /**
     * 调试模式
     */
    var debugMode: Boolean = builder.debugMode

    class Builder {
        internal var successCodeKey: String = "code"
        internal var successCode: Int = 200
        internal var debugMode: Boolean = BuildConfig.DEBUG

        fun setSuccessCodeKey(successCodeKey: String) =
            apply { this.successCodeKey = successCodeKey }

        fun setSuccessCode(successCode: Int) = apply { this.successCode = successCode }

        fun setDebugMode(debugMode: Boolean) = apply { this.debugMode = debugMode }

        fun build() = ToolkitConfig(this)
    }
}