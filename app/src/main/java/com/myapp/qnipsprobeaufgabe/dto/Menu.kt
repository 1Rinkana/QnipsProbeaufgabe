package com.myapp.qnipsprobeaufgabe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonData (
    @SerialName("Allergens") val allergens: Map<String, Allergen>,
    @SerialName("Products") val products: Map<String, Product>,
    @SerialName("Rows") val rows: List<Row>,
)

@Serializable
data class Allergen(
    @SerialName("Id") val id: String,
    @SerialName("Label") val label: String,
)

@Serializable
data class Price(
    @SerialName("Betrag") val betrag: Double,
)

@Serializable
data class Product(
    @SerialName("AllergenIds") val allergenIds: List<String>,
    @SerialName("ProductId") val productId: Int,
    @SerialName("Name") val name: String,
    @SerialName("Price") val price: Price
)

@Serializable
data class Day(
    @SerialName("Weekday") val weekday: Int,
    @SerialName("ProductIds") val productIds: List<ProductId>
)

@Serializable
data class ProductId(
    @SerialName("ProductId") val id: Int
)

@Serializable
data class Row(
    @SerialName("Name") val name: String,
    @SerialName("Days") val days: List<Day>
)