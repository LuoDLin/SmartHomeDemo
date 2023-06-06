package com.luodlin.smarthomedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.luodlin.smarthomedemo.ui.signin.SignInScreen
import com.luodlin.smarthomedemo.ui.theme.SmartHomeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val container = (application as App).container
        setContent {
            SmartHomeDemoTheme {
                SignInScreen(container)
            }
        }
    }
}




