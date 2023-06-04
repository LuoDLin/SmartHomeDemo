package com.luodlin.smarthomedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.luodlin.smarthomedemo.ui.State
import com.luodlin.smarthomedemo.ui.signin.SignInScreen
import com.luodlin.smarthomedemo.ui.theme.SmartHomeDemoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SmartHomeDemoTheme {
                Surface(tonalElevation = 2.dp) {
                    SignInScreen(
                        signIn = { _, _, _ -> },
                        signState = State.None,
                        getPhoneCode = { /*TODO*/ },
                        getPhoneCodeState = State.None
                    )
                }
            }
        }
    }
}




