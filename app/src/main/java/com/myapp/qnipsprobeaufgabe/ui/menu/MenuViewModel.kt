package com.myapp.qnipsprobeaufgabe.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.qnipsprobeaufgabe.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuViewModel(
    private val repository: MenuRepository,
): ViewModel() {
    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState.LoadingMenuState)
    val state: StateFlow<MenuState> = _state.asStateFlow()

    fun loadData() = viewModelScope.launch(Dispatchers.Default) {
        val result = withContext(Dispatchers.IO) { repository.getMenu() }
        delay(2000)
        when(result.isSuccess && result.getOrNull() != null) {
            true -> _state.value = MenuState.ReadyMenuState(result.getOrThrow())
            false -> _state.value = MenuState.ErrorMenuState(result.exceptionOrNull())
        }
    }

    init {
        loadData()
    }
}
