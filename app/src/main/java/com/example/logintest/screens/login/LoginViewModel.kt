package com.example.logintest.screens.registration.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.logintest.R
import com.example.logintest.core.ResourceHolder
import com.example.logintest.model.LoginInfo
import com.example.logintest.presentation.BaseStates
import com.example.logintest.screens.login.domain.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository,
    private val resourceHolder: ResourceHolder,
    private val router: NavController
) : ViewModel() {

    private var _state = MutableStateFlow<BaseStates>(BaseStates.Working)
    val state = _state.asStateFlow()

    private fun updateState(onUpdate: (BaseStates) -> BaseStates) {
        _state.update { oldState -> onUpdate(oldState) }
    }

    fun login(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkUser(LoginInfo(login = login, password = password))
                .onStart { updateState { BaseStates.Loading } }
                .onCompletion { updateState { BaseStates.Working } }
                .collect { result ->
                    if (!result)
                        updateState { BaseStates.Error(resourceHolder.getString(R.string.account_not_found)) }
                    else
                        toTabs()
                }
        }
    }

    fun toRegistration() {
        router.navigate(R.id.toRegistration)
    }

    private fun toTabs() {
        viewModelScope.launch(Dispatchers.Main) {
            router.navigate(R.id.toTabsFragment)
        }
    }
}