package com.myapp.qnipsprobeaufgabe.dto.repository

import com.myapp.qnipsprobeaufgabe.dto.JsonData

interface MenuRepository {
    suspend fun getMenu(): Result<JsonData>
}
