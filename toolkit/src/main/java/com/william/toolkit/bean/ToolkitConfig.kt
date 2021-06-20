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