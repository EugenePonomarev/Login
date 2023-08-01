package com.example.logintest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.logintest.model.LoginInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginDao {
    @Insert
    suspend fun insert(loginInfo: LoginInfo)

    @Delete
    suspend fun delete(loginInfo: LoginInfo)

    @Query("SELECT * FROM login_info_table order by id ")
    fun getLoginInfo(): Flow<LoginInfo>

    @Query("SELECT EXISTS (SELECT * FROM login_info_table where id=(:id))")
    fun checkCreatedAccountByID(id: Int): Boolean

    @Query("SELECT EXISTS (SELECT * FROM login_info_table where login=(:login))")
    fun getAutoResult(login: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM login_info_table where login=(:login) AND password=(:password))")
    fun checkLoginInfo(login: String, password: String): Boolean

    @Update
    suspend fun update(loginInfo: LoginInfo)
}