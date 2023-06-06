package com.luodlin.smarthomedemo.data.datasource.remote.entitys

data class SignInInfo(
    val phone: String,
    val password: String? = null,
    val validCode: String? = null,
    val type: Int
)