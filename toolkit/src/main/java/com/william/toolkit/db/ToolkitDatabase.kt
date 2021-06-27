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
import com.william.toolkit.bean.AppCrashBean


/**
 * author：William
 * date：2021/6/13 16:09
 * description：数据库
 */
@Database(version = 4, entities = [ApiRecordBean::class, AppCrashBean::class])
abstract class ToolkitDatabase : RoomDatabase() {

    abstract fun getRecordDao(): RecordDao

    abstract fun getCrashDao(): CrashDao

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
                .fallbackToDestructiveMigration()// 允许版本升级失败时（未找到版本迁移规则）丢弃所有数据，并重新创建表，否则会抛异常
                .fallbackToDestructiveMigrationOnDowngrade()// 允许版本降级失败时（未找到版本迁移规则）丢弃所有数据，并重新创建表，否则会抛异常
                .enableMultiInstanceInvalidation()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_1_4)
                .build()

        /**
         * Migrate from version 1 to version 2
         */
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ApiRecordBean ADD COLUMN httpCode INTEGER NOT NULL DEFAULT 0")
            }
        }

        /**
         * Migrate from version 2 to version 3
         */
        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ApiRecordBean ADD COLUMN errorMsg TEXT")
            }
        }

        /**
         * Migrate from version 3 to version 4
         */
        private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `AppCrashBean` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `time` INTEGER NOT NULL, `message` TEXT, `threadName` TEXT)")
            }
        }

        /**
         * Migrate from version 1 to version 4
         * faster path, ignore middle version
         */
        private val MIGRATION_1_4: Migration = object : Migration(1, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ApiRecordBean ADD COLUMN httpCode INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE ApiRecordBean ADD COLUMN errorMsg TEXT")
                database.execSQL("CREATE TABLE IF NOT EXISTS `AppCrashBean` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `time` INTEGER NOT NULL, `message` TEXT, `threadName` TEXT)")
            }
        }
    }

}