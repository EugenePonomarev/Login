package com.example.logintest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleDataAnswer(
    val result: Boolean = false,
    val message: String = ""
) : Parcelable
