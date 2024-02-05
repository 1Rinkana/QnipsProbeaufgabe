package com.myapp.qnipsprobeaufgabe.dto

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

object ClientAPI {
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
