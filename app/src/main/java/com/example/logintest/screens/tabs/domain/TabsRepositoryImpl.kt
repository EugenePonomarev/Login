package com.example.logintest.screens.tabs.domain

import com.example.logintest.database.LoginDao
import com.example.logintest.model.LoginInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TabsRepositoryImpl @Inject constructor(private val loginDao: LoginDao) : TabsRepository {

    override fun getLoginInfo(): Flow<LoginInfo> = flow {
        loginDao.getLoginInfo().collect { data ->
            emit(suspendCoroutine {
                it.resume(data)
            })
        }
    }.flowOn(Dispatchers.IO)
}
