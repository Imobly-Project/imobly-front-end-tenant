package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String? = null,
    val title:String = "",
    val properties: List<Property> = emptyList()
)
