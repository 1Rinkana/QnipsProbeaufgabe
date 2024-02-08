package com.myapp.qnipsprobeaufgabe.di

import com.myapp.qnipsprobeaufgabe.data.api.MenuApi
import com.myapp.qnipsprobeaufgabe.repository.MenuRepositoryImpl
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { MenuRepositoryImpl(MenuApi.createApi()) }

    viewModel { MenuViewModel(get()) }
}
