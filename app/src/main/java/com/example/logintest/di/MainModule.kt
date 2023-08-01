package com.example.logintest.di

import android.content.Context
import com.example.logintest.core.ResourceHolder
import com.example.logintest.core.ResourceHolderImpl
import com.example.logintest.database.LoginDao
import com.example.logintest.screens.login.domain.LoginRepository
import com.example.logintest.screens.login.domain.LoginRepositoryImpl
import com.example.logintest.screens.registration.domain.RegistrationRepository
import com.example.logintest.screens.registration.domain.RegistrationRepositoryImpl
import com.example.logintest.screens.tabs.domain.TabsRepository
import com.example.logintest.screens.tabs.domain.TabsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object MainModule {

    @Provides
    @ActivityRetainedScoped
    fun provideResourceHolder(@ApplicationContext context: Context): ResourceHolder {
        return ResourceHolderImpl(context)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideLoginRepository(loginDao: LoginDao): LoginRepository {
        return LoginRepositoryImpl(loginDao)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRegistrationRepository(
        loginDao: LoginDao,
        resourceHolder: ResourceHolder
    ): RegistrationRepository {
        return RegistrationRepositoryImpl(loginDao, resourceHolder)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideTabsRepository(
        loginDao: LoginDao
    ): TabsRepository {
        return TabsRepositoryImpl(loginDao)
    }
}