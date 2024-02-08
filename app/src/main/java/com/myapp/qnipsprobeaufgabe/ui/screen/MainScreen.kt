package com.myapp.qnipsprobeaufgabe.ui.screen

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuState
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModelFactory

@Composable
fun MainScreen(
    menuViewModel: MenuViewModel = viewModel(
        factory = MenuViewModelFactory()
    )
) {
    val state by menuViewModel.state.collectAsState()

    Crossfade(targetState = state, label = "Main") { currentState ->
        Surface {
            when (currentState) {
                is MenuState.LoadingMenuState -> LoadingScreen()
                is MenuState.ReadyMenuState -> SuccessScreen(menuViewModel)
                is MenuState.ErrorMenuState -> ErrorScreen(menuViewModel)
            }
        }
    }
}
