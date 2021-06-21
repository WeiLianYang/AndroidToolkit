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

package com.william.toolkit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.william.toolkit.bean.ApiRecordBean

/**
 * author：William
 * date：2021/6/13 16:09
 * description：记录表操作接口
 */
@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecord(bean: ApiRecordBean): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecordList(list: List<ApiRecordBean>): List<Long>

    @Query("delete FROM ApiRecordBean where id in (select id from ApiRecordBean limit :offset)")
    fun removeHeadRecord(offset: Int = 20): Int

    /**
     * 检索范围 (offset, offset + rows]
     * 检索范围 (startId, rows]
     */
//    @Query("select * from ApiRecordBean order by requestTime desc limit :offset,:rows")
//    @Query("select * from ApiRecordBean where id >= (select id from ApiRecordBean limit :offset,1) limit :rows")
    @Query("select * from ApiRecordBean where id > :startId order by requestTime desc limit :rows")
//    @Query("select * from ApiRecordBean where requestTime > :lastTime limit :rows")
    fun queryLimitRecord(startId: Long = 0, rows: Int = 10000): List<ApiRecordBean>

    @Query("select count(id) from ApiRecordBean")
    fun queryTotalRecord(): Long

    @Query("DELETE FROM ApiRecordBean")
    fun deleteAllRecord(): Int

}