package com.william.toolkit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.william.toolkit.bean.ApiRecordBean

/**
 * author：William
 * date：2021/6/13 16:09
 * description：数据库
 */
@Database(version = 1, entities = [ApiRecordBean::class])
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
                .build()
    }

}