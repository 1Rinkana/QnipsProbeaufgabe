package com.myapp.qnipsprobeaufgabe.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.qnipsprobeaufgabe.data.api.MenuApi
import com.myapp.qnipsprobeaufgabe.repository.MenuRepositoryImpl

class MenuViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(MenuRepositoryImpl(MenuApi.createApi())) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
