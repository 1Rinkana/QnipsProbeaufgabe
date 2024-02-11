package com.myapp.qnipsprobeaufgabe.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.myapp.qnipsprobeaufgabe.data.JsonData
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface MenuApi {
    @GET("/dbapi/ha")
    suspend fun getMenu(): Response<JsonData>
    companion object Factory {
        private const val API_URL = "https://my.qnips.io/"
        private val contentType = MediaType.get("application/json")

        fun createApi(): MenuApi {
            return Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
                .create(MenuApi::class.java)
        }
    }
}
