package com.myapp.qnipsprobeaufgabe.dto

import retrofit2.Response
import retrofit2.http.GET

interface MenuApi {
    @GET("/dbapi/ha")
    suspend fun getMenu(): Response<JsonData>
}
