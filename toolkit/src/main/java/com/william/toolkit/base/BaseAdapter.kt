package com.william.toolkit.base

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.william.toolkit.base.BaseViewHolder.Companion.create

/**
 * @author William
 * @date 2020-02-17 17:38
 * Class Comment：
 */
abstract class BaseAdapter<T>() : RecyclerView.Adapter<BaseViewHolder>() {
    private var mActivity: Activity? = null
    private var mFragment: Fragment? = null
    private val mList: ArrayList<T> = ArrayList()
    private var mOnItemClickListener: OnItemClickListener<T>? = null
    private var mOnItemLongClickListener: OnItemLongClickListener<T>? = null
    private var mOnItemChildClickListener: OnItemChildClickListener<T>? = null
    private var mOnItemChildLongClickListener: OnItemChildLongClickListener<T>? = null

    constructor(activity: Activity?) : this() {
        mActivity = activity
    }

    constructor(fragment: Fragment) : this(fragment.activity) {
        mFragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return create(mActivity, mFragment, inflateView(getLayoutResourceId(), parent))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindViewHolder(holder, position, mList[position])
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(
                holder,
                position,
                if (position >= mList.size) null else mList[position]
            )
        }
        holder.itemView.setOnLongClickListener {
            mOnItemLongClickListener?.onItemLongClick(
                holder,
                position,
                if (position >= mList.size) null else mList[position]
            )
            return@setOnLongClickListener true
        }
    }

    /**
     * 布局id
     *
     * @return r
     */
    protected abstract fun getLayoutResourceId(): Int

    /**
     * 绑定holder
     *
     * @param holder   h
     * @param position p
     * @param bean     b
     */
    protected open fun onBindViewHolder(holder: BaseViewHolder, position: Int, bean: T) {

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    /**
     * 通过布局ID获取View
     *
     * @param layoutResId l
     * @param parent      p
     * @return r
     */
    private fun inflateView(@LayoutRes layoutResId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    }

    /**
     * 获取该适配器的列表数据
     *
     * @return l
     */
    val data: List<T>
        get() = mList

    operator fun get(position: Int): T? {
        return if (position < 0 || position >= itemCount) {
            null
        } else mList[position]
    }

    operator fun set(position: Int, bean: T) {
        if (position < 0 || position >= itemCount) {
            return
        }
        mList[position] = bean
        notifyItemChanged(position)
    }

    fun add(bean: T?) {

    }

    fun add(position: Int, bean: T?) {
        if (position < 0 || position > itemCount) {
            return
        }
        bean?.let {
            mList.add(position, bean)
        }
    }

    fun addAll(beans: List<T>?) {
        beans?.let {
            mList.addAll(it)
        }
    }

    fun remove(position: Int): T? {
        return if (position >= 0 && position < mList.size) {
            mList.removeAt(position)
        } else null
    }

    fun remove(t: T?): Boolean {
        return if (t != null) {
            mList.remove(t)
        } else false
    }

    fun clear() {
        if (mList.isNotEmpty()) {
            mList.clear()
        }
    }

    fun clearAndNotifyDataSetChanged() {
        if (mList.isNotEmpty()) {
            mList.clear()
        }
        notifyDataSetChanged()
    }

    val isEmpty: Boolean
        get() = mList.isEmpty()

    val isNotEmpty: Boolean
        get() = mList.isNotEmpty()

    /**
     * 添加子项View到点击事件中
     *
     * @param viewId   控件Id
     * @param holder   BaseRvViewHolder
     * @param position 当前位置
     * @param bean     数据
     */
    @SuppressLint("CheckResult")
    protected fun addOnChildClickListener(
        @IdRes viewId: Int,
        holder: BaseViewHolder,
        position: Int,
        bean: T
    ) {
        val view = holder.getView<View>(viewId)
        view?.setOnClickListener {
            mOnItemChildClickListener?.onItemChildCLick(it, holder, position, bean)
        }
    }

    /**
     * 添加子项View到长按事件中
     *
     * @param viewId   控件Id
     * @param holder   BaseRvViewHolder
     * @param position 当前位置
     * @param bean     数据
     */
    @Suppress("unused")
    protected fun addOnChildLongClickListener(
        @IdRes viewId: Int,
        holder: BaseViewHolder,
        position: Int,
        bean: T
    ) {
        holder.getView<View>(viewId)?.setOnLongClickListener { v: View ->
            mOnItemChildLongClickListener?.onItemChildLongClick(v, holder, position, bean)
            true
        }
    }

    fun setOnItemClickListener(OnItemClickListener: OnItemClickListener<T>): BaseAdapter<T> {
        mOnItemClickListener = OnItemClickListener
        return this
    }

    fun setOnItemLongClickListener(OnItemLongClickListener: OnItemLongClickListener<T>): BaseAdapter<T> {
        mOnItemLongClickListener = OnItemLongClickListener
        return this
    }

    fun setOnItemChildClickListener(OnItemChildClickListener: OnItemChildClickListener<T>): BaseAdapter<T> {
        mOnItemChildClickListener = OnItemChildClickListener
        return this
    }

    @Suppress("unused")
    fun setOnItemChildLongClickListener(OnItemChildLongClickListener: OnItemChildLongClickListener<T>): BaseAdapter<T> {
        mOnItemChildLongClickListener = OnItemChildLongClickListener
        return this
    }

    interface OnItemClickListener<T> {
        /**
         * 条目点击事件
         *
         * @param holder   h
         * @param position p
         * @param bean     b
         */
        fun onItemClick(holder: BaseViewHolder, position: Int, bean: T?)
    }

    interface OnItemLongClickListener<T> {
        /**
         * 条目长按事件
         *
         * @param holder   h
         * @param position p
         * @param bean     b
         */
        fun onItemLongClick(holder: BaseViewHolder, position: Int, bean: T?)
    }

    interface OnItemChildClickListener<T> {
        /**
         * 条目子项点击事件
         *
         * @param view     v
         * @param holder   h
         * @param position p
         * @param bean     b
         */
        fun onItemChildCLick(view: View, holder: BaseViewHolder, position: Int, bean: T)
    }

    interface OnItemChildLongClickListener<T> {
        /**
         * 条目子项长按事件
         *
         * @param view     v
         * @param holder   h
         * @param position p
         * @param bean     b
         */
        fun onItemChildLongClick(view: View, holder: BaseViewHolder, position: Int, bean: T)
    }

}