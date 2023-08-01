package com.example.logintest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_info_table")
data class LoginInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "password") val password: String
)
