package com.yandex.finance.core.localdb.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.yandex.finance.core.localdb.dao.*
import com.yandex.finance.core.localdb.entity.*

@Database(
    entities = [
        TransactionEntity::class,
        AccountEntity::class,
        CategoryEntity::class,
        SyncMetadataEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class YandexFinanceDatabase : RoomDatabase() {
    
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun syncMetadataDao(): SyncMetadataDao
    
    companion object {
        const val DATABASE_NAME = "yandex_finance_database"
        
        @Volatile
        private var INSTANCE: YandexFinanceDatabase? = null
        
        fun getInstance(context: Context): YandexFinanceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YandexFinanceDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(false) // Чтобы не мучится с миграциями, если что :)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 