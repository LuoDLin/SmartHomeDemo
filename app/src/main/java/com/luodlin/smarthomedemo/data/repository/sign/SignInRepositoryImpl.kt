package com.luodlin.smarthomedemo.data.repository.sign

import android.util.Log
import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.datasource.local.LocalDataSource
import com.luodlin.smarthomedemo.data.datasource.remote.RemoteDataSource
import com.luodlin.smarthomedemo.data.datasource.remote.entitys.NetworkSignIn
import com.luodlin.smarthomedemo.data.model.Sign
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SignInRepositoryImpl(
    private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource
) : SignInRepository {

    private val _signIn = MutableStateFlow(localDataSource.readSignInInfo())
    override val signInFlow: Flow<Result<Sign>>
        get() = _signIn

    private fun NetworkSignIn.asSign(): Sign = Sign(signInType, token)

    override suspend fun fetchPhoneCode(phone: String): Result<Nothing> =
        remoteDataSource.fetchCode(phone)

    override suspend fun phoneSignIn(phone: String, code: String) {
        delay(1500)
        Log.i("SignInRepositoryImpl","phoneSignIn")
        val result = remoteDataSource.phoneSignIn(phone, code)
        emitNetWorkSignInResult(result)
    }

    override suspend fun passwordSignIn(phone: String, password: String) {
        delay(1500)
        Log.i("SignInRepositoryImpl","passwordSignIn")
        val result = remoteDataSource.passwordSignIn(phone, password)
        emitNetWorkSignInResult(result)
    }

    override suspend fun logout(): Result<Nothing> {
        delay(1500)
        return Result.Success()
    }

    private suspend fun emitNetWorkSignInResult(data: Result<NetworkSignIn>) {
        val result = when (data) {
            is Result.Error -> Result.Error(data.exception)
            is Result.Success -> Result.Success(data.data!!.asSign())
        }

        when(result){
            is Result.Error -> Log.i("SignInRepositoryImpl","emitNetWorkSignInResult:Error")
            is Result.Success -> Log.i("SignInRepositoryImpl","emitNetWorkSignInResult:Success")
        }
        _signIn.value = result
        localDataSource.saveSignInInfo(result)
    }
}