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

import android.app.Activity
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
 * @author William
 * @date 2019-06-28 11:28
 * Class Comment：
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mViews: SparseArray<View?> = SparseArray()
    private var mActivity: Activity? = null
    private var mFragment: Fragment? = null

    constructor(activity: Activity?, itemView: View) : this(itemView) {
        mActivity = activity
    }

    constructor(activity: Activity?, fragment: Fragment?, itemView: View) : this(itemView) {
        mActivity = activity
        mFragment = fragment
    }

    /**
     * 根据资源Id获取相应的View
     *
     * @param viewId id
     * @param <T>    t
     * @return v
    </T> */
    @Suppress("UNCHECKED_CAST")
    fun <T : View?> getView(@IdRes viewId: Int): T? {
        var view = mViews[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T?
    }

    /**
     * 设置View可见或不可见
     *
     * @param viewId     v
     * @param visibility View.VISIBLE,View.GONE,View.INVISIBLE
     */
    fun setVisibility(@IdRes viewId: Int, visibility: Int): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.visibility = visibility
        return this
    }

    /**
     * TextView设置文字
     *
     * @param viewId  v
     * @param content c
     */
    fun setText(@IdRes viewId: Int, content: String?): BaseViewHolder {
        val tv = getView<TextView>(viewId)
        tv?.text = content
        return this
    }

    /**
     * TextView设置文字
     *
     * @param viewId  v
     * @param content c
     */
    fun setText(@IdRes viewId: Int, content: Spannable?): BaseViewHolder {
        val tv = getView<TextView>(viewId)
        tv?.text = content
        return this
    }

    /**
     * TextView设置加粗h或费加粗字体
     *
     * @param viewId   v
     * @param typeface style
     */
    fun setTextStyle(@IdRes viewId: Int, typeface: Int): BaseViewHolder {
        val tv = getView<TextView>(viewId)
        tv?.typeface = Typeface.defaultFromStyle(typeface)
        return this
    }

    /**
     * ImageView设置图片
     *
     * @param viewId        v
     * @param imgResourceId i
     */
    fun setImageResource(@IdRes viewId: Int, @DrawableRes imgResourceId: Int): BaseViewHolder {
        val iv = getView<ImageView>(viewId)
        iv?.setImageResource(imgResourceId)
        return this
    }

    /**
     * ImageView设置图片
     *
     * @param viewId   v
     * @param drawable d
     */
    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): BaseViewHolder {
        val iv = getView<ImageView>(viewId)
        iv?.setImageDrawable(drawable)
        return this
    }

    /**
     * 设置背景颜色
     *
     * @param viewId v
     * @param color  c
     */
    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BaseViewHolder {
        val iv = getView<View>(viewId)
        iv?.setBackgroundColor(color)
        return this
    }

    /**
     * 设置背景资源
     *
     * @param viewId   v
     * @param drawable d
     */
    fun setBackgroundDrawable(@IdRes viewId: Int, drawable: Drawable?): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.background = drawable
        return this
    }

    /**
     * 设置图标资源是否选中
     *
     * @param viewId   v
     * @param selected s
     */
    fun setSelected(@IdRes viewId: Int, selected: Boolean): BaseViewHolder {
        val iv = getView<View>(viewId)
        iv?.isSelected = selected
        return this
    }

    /**
     * 设置字体加粗样式
     *
     * @param viewId   v
     * @param selected s
     */
    @Suppress("unused")
    fun setBoldFont(@IdRes viewId: Int, selected: Boolean): BaseViewHolder {
        val tv = getView<View>(viewId)
        if (tv is TextView) {
            tv.typeface = if (selected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        }
        return this
    }

    /**
     * 设置图标资源是否可点击
     *
     * @param viewId  v
     * @param enabled e
     */
    fun setEnabled(@IdRes viewId: Int, enabled: Boolean): BaseViewHolder {
        val iv = getView<View>(viewId)
        iv?.isEnabled = enabled
        return this
    }

    /**
     * 设置文字字体
     *
     * @param viewId int
     * @param tf     Typeface
     */
    fun setTypeface(@IdRes viewId: Int, tf: Typeface?): BaseViewHolder {
        val iv = getView<TextView>(viewId)
        iv?.typeface = tf
        return this
    }

    /**
     * 设置文字颜色
     *
     * @param viewId int
     * @param color  int
     */
    fun setTextColor(@IdRes viewId: Int, @ColorInt color: Int): BaseViewHolder {
        val iv = getView<TextView>(viewId)
        iv?.setTextColor(color)
        return this
    }

    fun setOnClickListener(@IdRes viewId: Int, listener: View.OnClickListener?): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.setOnClickListener(listener)
        return this
    }

    companion object {
        fun create(itemView: View): BaseViewHolder {
            return BaseViewHolder(itemView)
        }

        fun create(activity: Activity?, itemView: View): BaseViewHolder {
            return BaseViewHolder(activity, itemView)
        }

        @JvmStatic
        fun create(activity: Activity?, fragment: Fragment?, itemView: View): BaseViewHolder {
            return BaseViewHolder(activity, fragment, itemView)
        }
    }

}