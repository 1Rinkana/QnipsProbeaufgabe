package com.myapp.qnipsprobeaufgabe.ui.menu

import com.myapp.qnipsprobeaufgabe.dto.JsonData

sealed class MenuState {
    data object LoadingMenuState : MenuState()

    class ReadyMenuState(
        val menu: JsonData,
    ): MenuState()

    class ErrorMenuState(
        val throwable: Throwable?,
    ): MenuState()
}

