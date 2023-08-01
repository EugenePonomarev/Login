package com.example.logintest.screens.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logintest.core.ResourceHolder
import com.example.logintest.model.LoginInfo
import com.example.logintest.screens.tabs.domain.TabsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabsViewModel @Inject constructor(
    private val repository: TabsRepository
) : ViewModel() {
    private val _data = MutableStateFlow(LoginInfo(login = "", password = ""))
    var data = _data.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getLoginInfo()
                .collect {
                    _data.value = it
                }
        }
    }
}