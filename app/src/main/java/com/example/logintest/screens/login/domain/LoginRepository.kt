package com.example.logintest.screens.login.domain

import com.example.logintest.model.LoginInfo
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun checkUser(loginInfo: LoginInfo): Flow<Boolean>

}