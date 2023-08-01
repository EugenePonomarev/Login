package com.example.logintest.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.R
import com.example.logintest.core.ResourceHolder
import com.example.logintest.model.RegistrationInfo
import com.example.logintest.presentation.BaseStates
import com.example.logintest.screens.registration.domain.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: RegistrationRepository,
    private val resource: ResourceHolder
) : ViewModel() {

    private var _state = MutableStateFlow<BaseStates>(BaseStates.Working)
    val state = _state.asStateFlow()

    private fun updateState(onUpdate: (BaseStates) -> BaseStates) {
        _state.update { oldState -> onUpdate(oldState) }
    }


    fun registration(registrationInfo: RegistrationInfo) {
        viewModelScope.launch {
            if (registrationInfo.password != registrationInfo.confirmPassword)
                updateState { BaseStates.Error(resource.getString(R.string.password_mismatch)) }
            else {
                repository.checkUser(registrationInfo.login)
                    .onStart { updateState { BaseStates.Loading } }
                    .onCompletion { updateState { BaseStates.Working }}
                    .collect { result ->
                        if (result.result) {
                            repository.registration(registrationInfo)

                            updateState { BaseStates.Success }
                        } else {
                            BaseStates.Error(result.message)
                        }
                    }
            }
        }
    }
}