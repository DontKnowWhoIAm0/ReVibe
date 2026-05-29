package com.revibe.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.revibe.core.db.dao.CartDao
import com.revibe.core.db.entity.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
abstract class ReVibeDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile private var INSTANCE: ReVibeDatabase? = null

        fun getInstance(context: Context): ReVibeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext, ReVibeDatabase::class.java, "revibe.db")
                    .build().also { INSTANCE = it }
            }
    }
}
