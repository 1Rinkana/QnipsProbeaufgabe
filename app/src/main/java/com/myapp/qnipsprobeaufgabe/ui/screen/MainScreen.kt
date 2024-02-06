package com.myapp.qnipsprobeaufgabe.ui.screen

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuState
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel

@Composable
fun MainScreen(viewModel: MenuViewModel) {
    val state by viewModel.state.collectAsState()

    Surface {
        when(state) {
            is MenuState.LoadingMenuState -> LoadingScreen()
            is MenuState.ReadyMenuState -> SuccessScreen(viewModel)
            is MenuState.ErrorMenuState -> ErrorScreen(viewModel)
        }
    }
}