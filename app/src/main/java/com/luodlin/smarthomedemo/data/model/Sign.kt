package com.luodlin.smarthomedemo.data.model

data class Sign(
    val isSignIn: Boolean = false,
    val signInType: Int? = null,
    val token: String? = null,
    val message: String? = null
)