package com.myapp.qnipsprobeaufgabe.ui.menu

import com.myapp.qnipsprobeaufgabe.dto.JsonData
import java.lang.Exception

sealed class MenuState {
    data object LoadingMenuState : MenuState()

    class ReadyMenuState(
        val menu: JsonData? = null,
    ): MenuState()

    class ErrorMenuState(
        val throwable: Throwable?,
    ): MenuState()
}

