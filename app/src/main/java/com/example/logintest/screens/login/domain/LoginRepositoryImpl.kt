package com.example.logintest.screens.login.domain

import com.example.logintest.database.LoginDao
import com.example.logintest.model.LoginInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginRepositoryImpl @Inject constructor(
    private val loginDao: LoginDao
) : LoginRepository {

    override suspend fun checkUser(loginInfo: LoginInfo): Flow<Boolean> = flow {
        emit(suspendCoroutine {
            it.resume(
                loginDao.checkLoginInfo(
                    loginInfo.login,
                    loginInfo.password
                )
            )
        })
    }.flowOn(Dispatchers.IO)
}