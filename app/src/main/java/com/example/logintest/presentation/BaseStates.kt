package com.example.logintest.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class BaseStates : Parcelable {
    object Working : BaseStates()
    object Loading : BaseStates()
    object Success : BaseStates()
    class Error(val message: String) : BaseStates()
}
