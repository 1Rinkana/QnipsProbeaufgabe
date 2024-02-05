package com.myapp.qnipsprobeaufgabe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.qnipsprobeaufgabe.dto.ClientAPI
import com.myapp.qnipsprobeaufgabe.dto.repository.MenuRepositoryImpl
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuState
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MenuViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MenuViewModel(MenuRepositoryImpl(ClientAPI.createApi())) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen(viewModel)
        }
        viewModel.loadData()
    }
}

@Composable
fun AppScreen(viewModel: MenuViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
           when(state) {
               is MenuState.LoadingMenuState -> LoadingScreen()
               is MenuState.ReadyMenuState -> SuccessScreen(viewModel)
               is MenuState.ErrorMenuState -> ErrorScreen(viewModel)
           }
        }
    }
}

@Composable
fun LoadingScreen() {

}

@Composable
fun SuccessScreen(viewModel: MenuViewModel) {

}

@Composable
fun ErrorScreen(viewModel: MenuViewModel) {

}
