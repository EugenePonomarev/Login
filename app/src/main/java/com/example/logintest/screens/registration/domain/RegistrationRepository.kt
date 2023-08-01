package com.example.logintest.screens.registration.domain

import com.example.logintest.model.RegistrationInfo
import com.example.logintest.model.SimpleDataAnswer
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun checkUser(login: String): Flow<SimpleDataAnswer>

    suspend fun registration(registrationInfo: RegistrationInfo)
}