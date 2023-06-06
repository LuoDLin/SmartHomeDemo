package com.luodlin.smarthomedemo.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.luodlin.smarthomedemo.data.Result
import com.luodlin.smarthomedemo.data.repository.sign.SignInRepository
import com.luodlin.smarthomedemo.ui.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignInViewModel(private val signInRepository: SignInRepository) : ViewModel() {

    private val _loginUiState = MutableStateFlow(SignInUiState(State.None, State.None, null))
    val uiState = _loginUiState.asStateFlow()


    //1密码登录 2验证码
    fun signIn(userName: String, verify: String, signInType: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _loginUiState.update {
                    it.copy(
                        signInUiState = State.Loading, errorMessages = null
                    )
                } //加载中
                when (signInType) {    //登录操作
                    1 -> signInRepository.passwordSignIn(userName, verify)
                    2 -> signInRepository.phoneSignIn(userName, verify)
                }
                Log.i("SignInViewModel", "登录中")
            }
        }
    }

    fun fetchPhoneCode(phone: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _loginUiState.update {
                    it.copy(
                        fetchPhoneCodeState = State.Loading, errorMessages = null
                    )
                }
                when (val result = signInRepository.fetchPhoneCode(phone)) {
                    is Result.Error -> _loginUiState.update {
                        it.copy(
                            fetchPhoneCodeState = State.Failed,
                            errorMessages = result.exception.message ?: "获取验证码未知错误"
                        )
                    }

                    is Result.Success -> _loginUiState.update {
                        it.copy(
                            fetchPhoneCodeState = State.Success, errorMessages = "获取验证码成功"
                        )
                    }
                }
            }
        }
    }

    companion object {
        fun provideFactory(
            repository: SignInRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignInViewModel(repository) as T
            }
        }
    }

    init {
        viewModelScope.launch {
            signInRepository.signInFlow.collect { signInResult ->
                when (signInResult) {
                    is Result.Error -> Log.i("SignInViewModel", "登录失败")
                    is Result.Success -> Log.i("SignInViewModel", "登录成功")
                }
                _loginUiState.update {
                    when (signInResult) {
                        is Result.Error -> it.copy(
                            signInUiState = State.Failed,
                            errorMessages = signInResult.exception.message ?: "登录出现未知错误"
                        )

                        is Result.Success -> it.copy(
                            signInUiState = State.Success, errorMessages = "登录成功"
                        )
                    }
                }
            }
        }
    }

    data class SignInUiState(
        val signInUiState: State, val fetchPhoneCodeState: State, val errorMessages: String?
    )

}