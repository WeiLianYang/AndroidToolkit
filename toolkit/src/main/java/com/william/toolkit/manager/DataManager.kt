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

import com.william.toolkit.ToolkitPanel
import com.william.toolkit.bean.ApiRecordBean
import com.william.toolkit.bean.AppCrashBean
import com.william.toolkit.db.ToolkitDatabase
import com.william.toolkit.ext.logd
import com.william.toolkit.ext.logi
import com.william.toolkit.ext.logw
import kotlinx.coroutines.flow.Flow
import kotlin.concurrent.thread


/**
 * @author William
 * @date 2021/6/13 16:11
 * Class Comment：数据管理类
 */
object DataManager {

    // 最多存储1万条，超过后就移除头部数据
    private const val cacheCount = 10000

    /**
     * 数据库表[com.william.toolkit.db.RecordDao]操作对象
     */
    private val dao = ToolkitDatabase.getInstance(ToolkitPanel.appContext).getRecordDao()

    /**
     * 数据库表[com.william.toolkit.db.CrashDao]操作对象
     */
    private val crashDao = ToolkitDatabase.getInstance(ToolkitPanel.appContext).getCrashDao()

    /**
     * 保存单条记录
     */
    @JvmStatic
    fun saveRecord(bean: ApiRecordBean?) {
        if (!ToolkitPanel.isDebugMode) {
            return
        }
        if (bean == null) {
            return
        }
        thread {
            removeTopRecord()
            dao.insertRecord(bean)
            "record save success：${bean.url}".logi()
        }
    }

    /**
     * 保存记录列表
     */
    @JvmStatic
    fun saveRecordList(list: List<ApiRecordBean>?) {
        if (!ToolkitPanel.isDebugMode) {
            return
        }
        if (list.isNullOrEmpty()) {
            return
        }
        thread {
            removeTopRecord(list.size)
            dao.insertRecordList(list)
            "record save success：${list.size}".logi()
        }
    }

    private fun removeTopRecord(offset: Int = 1) {
        val totalCount = dao.queryTotalRecord()
        "record totalCount: $totalCount".logd()
        if (totalCount >= cacheCount) {
            dao.removeHeadRecord(offset)
            "top record removed: $offset".logw()
        }
    }

    /**
     * 读取数据列表
     */
    internal fun getRecordList(index: Long): Flow<List<ApiRecordBean>> {
        return dao.queryLimitRecord(index)
    }

    /**
     * 清除数据表
     */
    internal fun deleteAllRecord(): Boolean {
        val rows = dao.deleteAllRecord()
        val flag = rows > 0
        "record delete rows: $rows".logi()
        return flag
    }

    /**
     * 保存崩溃信息
     */
    @JvmStatic
    fun saveCrash(e: Throwable?, t: Thread? = null) {
        if (!ToolkitPanel.isDebugMode) {
            return
        }
        e ?: return
        val thread = t ?: Thread.currentThread()
        thread {
            val sb = StringBuilder()
            e.stackTrace.forEach {
                sb.append("${AppCrashBean.TITLE_ARRAY[3]}$it").append("\n")
            }
            val bean = AppCrashBean(
                time = System.currentTimeMillis(),
                cause = "$e",
                message = sb.toString(),
                threadName = thread?.name
            )
            crashDao.insert(bean)
            "crash save success：${bean.threadName}".logi()
        }
    }

    /**
     * 读取崩溃信息
     */
    internal fun getCrashInfo(): Flow<AppCrashBean> {
        return crashDao.query()
    }

}