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

class SignInRepositoryImpl(
    private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource
) : SignInRepository {

    private val _signIn = MutableStateFlow(localDataSource.readSignInInfo())
    override val signInFlow: Flow<Sign>
        get() = _signIn

    private fun NetworkSignIn.asSign(): Sign =
        Sign(code == 200, data?.signInType, data?.token, message)

    override suspend fun fetchPhoneCode(phone: String): Result<Nothing> {
        return try {
            remoteDataSource.fetchCode(phone, 0).run {
                if (code == 200) Result.Success()
                else Result.Error(Exception(message))
            }
        } catch (e: Exception) {
            Log.i("fetchPhoneCode", "" + e.message)
            Result.Error(e)
        }
    }

    override suspend fun phoneSignIn(phone: String, code: String) {
        _signIn.emit(
            try {
                remoteDataSource.phoneSignIn(phone, code).asSign()
            } catch (e: Exception) {
                Sign(message = e.message)
            }
        )
    }

    override suspend fun passwordSignIn(phone: String, password: String) {
        _signIn.emit(
            try {
                remoteDataSource.passwordSignIn(phone, password).asSign()
            } catch (e: Exception) {
                Sign(message = e.message)
            }
        )
    }

    override suspend fun logout(): Result<Nothing> {
        delay(1500)
        return Result.Success()
    }


}

