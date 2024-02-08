package com.myapp.qnipsprobeaufgabe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myapp.qnipsprobeaufgabe.di.appModule
import com.myapp.qnipsprobeaufgabe.ui.screen.MainScreen
import com.myapp.qnipsprobeaufgabe.ui.theme.QnipsProbeaufgabeTheme
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QnipsProbeaufgabeTheme {
                MainScreen()
            }
        }
    }
}
