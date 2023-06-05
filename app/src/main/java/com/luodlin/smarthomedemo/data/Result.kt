package com.luodlin.smarthomedemo.data

sealed interface Result<out R> {
    data class Success<out T>(val data: T? = null) : Result<T>
    data class Error(val exception: Exception) : Result<Nothing>

}
