package com.myapp.qnipsprobeaufgabe.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.qnipsprobeaufgabe.data.Day
import com.myapp.qnipsprobeaufgabe.data.JsonData
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
        when (result.isSuccess && result.getOrNull() != null) {
            true -> _state.value = MenuState.ReadyMenuState(menuCheckout(result.getOrThrow()))
            false -> _state.value = MenuState.ErrorMenuState(result.exceptionOrNull())
        }
    }

    private fun menuCheckout(menu: JsonData): JsonData {
        menu.rows.forEach { row ->
            val dayIndexes = mutableListOf<Int>(0, 1, 2, 3, 4, 5, 6)
            row.days.forEach { day ->
                if (dayIndexes.contains(day.weekday)) {
                    dayIndexes.remove(day.weekday)
                }
            }
            if (dayIndexes.isNotEmpty()) {
                dayIndexes.forEach { dayIndex ->
                    menu.rows[menu.rows.indexOf(row)].days.add(
                        dayIndex,
                        Day(weekday = dayIndex, productIds = emptyList())
                    )
                }
            }

        }

        return menu
    }
}
