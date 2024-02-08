package com.myapp.qnipsprobeaufgabe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.myapp.qnipsprobeaufgabe.ui.screen.MainScreen
import com.myapp.qnipsprobeaufgabe.ui.theme.QnipsProbeaufgabeTheme

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
