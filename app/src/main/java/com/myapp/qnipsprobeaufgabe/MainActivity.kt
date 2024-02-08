package com.myapp.qnipsprobeaufgabe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.qnipsprobeaufgabe.data.api.MenuApi
import com.myapp.qnipsprobeaufgabe.repository.MenuRepositoryImpl
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel
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
