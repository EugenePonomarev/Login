package com.example.logintest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.logintest.model.LoginInfo

@Database(
    entities = [LoginInfo::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLoginDao(): LoginDao
}