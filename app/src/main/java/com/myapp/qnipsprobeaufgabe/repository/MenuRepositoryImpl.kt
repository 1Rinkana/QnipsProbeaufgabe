package com.myapp.qnipsprobeaufgabe.repository

import com.myapp.qnipsprobeaufgabe.data.JsonData
import com.myapp.qnipsprobeaufgabe.data.api.MenuApi

class MenuRepositoryImpl(private val api: MenuApi): MenuRepository {
    override suspend fun getMenu(): Result<JsonData> {
        api.getMenu().let { response ->
            if (response.isSuccessful && response.body() != null) {
                return Result.success(response.body()!!)
            } else {
                return Result.failure(Exception("Error ${response.code()} to get users  ${response.errorBody()}"))
            }
        }
    }
}
