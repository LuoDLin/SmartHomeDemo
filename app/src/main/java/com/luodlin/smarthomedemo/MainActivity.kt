package com.luodlin.smarthomedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.luodlin.smarthomedemo.data.datasource.local.FakeLocalDataSource
import com.luodlin.smarthomedemo.data.datasource.local.LocalDataSource
import com.luodlin.smarthomedemo.data.datasource.remote.FakeRemoteDataSource
import com.luodlin.smarthomedemo.data.datasource.remote.RemoteDataSource
import com.luodlin.smarthomedemo.data.repository.sign.SignInRepositoryImpl
import com.luodlin.smarthomedemo.ui.signin.SignInScreen
import com.luodlin.smarthomedemo.ui.signin.SignInViewModel
import com.luodlin.smarthomedemo.ui.theme.SmartHomeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val localDataSource: LocalDataSource = FakeLocalDataSource()
        val remoteDataSource: RemoteDataSource = FakeRemoteDataSource()
        val signInRepository = SignInRepositoryImpl(localDataSource, remoteDataSource)
        val vm by viewModels<SignInViewModel>(factoryProducer = {
            SignInViewModel.provideFactory(
                signInRepository
            )
        })
        setContent {
            SmartHomeDemoTheme {
                SignInScreen(viewModel = vm)
            }
        }
    }
}




