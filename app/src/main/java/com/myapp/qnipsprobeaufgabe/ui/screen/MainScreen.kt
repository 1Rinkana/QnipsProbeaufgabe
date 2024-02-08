package com.myapp.qnipsprobeaufgabe.ui.screen

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuState
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(vm: MenuViewModel = koinViewModel()) {
    val state by vm.state.collectAsState()

    Crossfade(targetState = state, label = "Main") { currentState ->
        Surface {
            when (currentState) {
                is MenuState.LoadingMenuState -> LoadingScreen()
                is MenuState.ReadyMenuState -> SuccessScreen(vm)
                is MenuState.ErrorMenuState -> ErrorScreen(vm)
            }
        }
    }
}
