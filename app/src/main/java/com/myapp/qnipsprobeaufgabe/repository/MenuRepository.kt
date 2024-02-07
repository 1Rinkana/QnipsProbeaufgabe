package com.myapp.qnipsprobeaufgabe.repository

import com.myapp.qnipsprobeaufgabe.data.JsonData

interface MenuRepository {
    suspend fun getMenu(): Result<JsonData>
}
