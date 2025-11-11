package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Property(
    val id: String? = null,
    val title: String = "",
    val description: String = "",
    val monthlyRent: String = "",
    val pathImages: List<String> = emptyList(),
    val available: Boolean = false,
    var address: Address = Address(),
    val bedrooms: String = "",
    val bathrooms: String = "",
    val area: String = "",
    val garageSpaces: String = "",
    val category: Category = Category()
)
