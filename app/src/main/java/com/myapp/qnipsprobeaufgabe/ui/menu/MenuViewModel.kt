package com.myapp.qnipsprobeaufgabe.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.qnipsprobeaufgabe.dto.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuViewModel(
    private val repository: MenuRepository,
): ViewModel() {
    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState.LoadingMenuState)
    val state: StateFlow<MenuState> = _state

    fun loadData() = viewModelScope.launch {
        val result = withContext(Dispatchers.IO) { repository.getMenu() }
        when(result.isSuccess && result.getOrNull() != null) {
            true -> _state.value = MenuState.ReadyMenuState(result.getOrThrow())
            false -> _state.value = MenuState.ErrorMenuState(result.exceptionOrNull())
        }
    }
}