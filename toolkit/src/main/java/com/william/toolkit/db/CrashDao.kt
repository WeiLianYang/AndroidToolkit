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
import com.william.toolkit.bean.AppCrashBean
import kotlinx.coroutines.flow.Flow

/**
 * author：William
 * date：2021/6/27 12:30
 * description：崩溃数据表操作接口
 */
@Dao
interface CrashDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bean: AppCrashBean): Long

    @Query("select * from AppCrashBean order by time desc limit 1")
    fun query(): Flow<AppCrashBean>

}