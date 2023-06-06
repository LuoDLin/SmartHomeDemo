package com.luodlin.smarthomedemo.data.datasource.remote.entitys

data class NetworkSignInData(val signInType: Int, val token: String)

class NetworkSignIn(val code: Int = -1, val message: String? = null, val data: NetworkSignInData? = null)