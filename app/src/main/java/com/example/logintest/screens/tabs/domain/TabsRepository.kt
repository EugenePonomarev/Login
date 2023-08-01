package com.example.logintest.screens.tabs.domain

import com.example.logintest.model.LoginInfo
import kotlinx.coroutines.flow.Flow

interface TabsRepository {
    fun getLoginInfo(): Flow<LoginInfo>
}