package com.example.logintest.screens.registration.domain

import com.example.logintest.R
import com.example.logintest.core.ResourceHolder
import com.example.logintest.database.LoginDao
import com.example.logintest.model.LoginInfo
import com.example.logintest.model.RegistrationInfo
import com.example.logintest.model.SimpleDataAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RegistrationRepositoryImpl @Inject constructor(
    private val loginDao: LoginDao,
    private val resourceHolder: ResourceHolder
) : RegistrationRepository {
    override suspend fun checkUser(login: String): Flow<SimpleDataAnswer> = flow {

        emit(suspendCoroutine {
            if (loginDao.checkCreatedAccountByID(1))
                it.resume(
                    SimpleDataAnswer(
                        false,
                        resourceHolder.getString(R.string.only_one_account_created)
                    )
                )
            else {
                if (loginDao.getAutoResult(login))
                    it.resume(
                        SimpleDataAnswer(
                            false,
                            resourceHolder.getString(R.string.account_already_created)
                        )
                    )
                else
                    it.resume(SimpleDataAnswer(true))
            }
        })
    }.flowOn(Dispatchers.IO)

    override suspend fun registration(registrationInfo: RegistrationInfo) {
        loginDao.insert(
            LoginInfo(
                id = 1,
                login = registrationInfo.login,
                password = registrationInfo.password
            )
        )
    }
}