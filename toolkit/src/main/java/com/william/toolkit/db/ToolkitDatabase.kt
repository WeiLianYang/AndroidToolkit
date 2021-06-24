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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.william.toolkit.bean.ApiRecordBean


/**
 * author：William
 * date：2021/6/13 16:09
 * description：数据库
 */
@Database(version = 3, entities = [ApiRecordBean::class])
abstract class ToolkitDatabase : RoomDatabase() {

    abstract fun getRecordDao(): RecordDao

    companion object {

        @Volatile
        private var INSTANCE: ToolkitDatabase? = null

        fun getInstance(context: Context): ToolkitDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ToolkitDatabase::class.java, "wyc_toolkit.db"
            )
                .fallbackToDestructiveMigration()
                .enableMultiInstanceInvalidation()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_1_3)
                .build()

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ApiRecordBean ADD COLUMN httpCode INTEGER NOT NULL DEFAULT 0")
            }
        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ApiRecordBean ADD COLUMN errorMsg TEXT")
            }
        }

        private val MIGRATION_1_3: Migration = object : Migration(1, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ApiRecordBean ADD COLUMN httpCode INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE ApiRecordBean ADD COLUMN errorMsg TEXT")
            }
        }
    }

}