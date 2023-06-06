package com.luodlin.smarthomedemo.data.repository.sign

import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.model.Sign
import kotlinx.coroutines.flow.Flow


interface SignInRepository {

    val signInFlow: Flow<Sign>

    /**
     * 获取验证码
     */
    suspend fun fetchPhoneCode(phone: String): Result<Nothing>

    /**
     * 验证码登录
     */
    suspend fun phoneSignIn(phone: String, code: String)

    /**
     * 密码登录
     */
    suspend fun passwordSignIn(phone: String, password: String)

    /**
     * 退出
     */
    suspend fun logout(): Result<Nothing>

}