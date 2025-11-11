package com.imobly.imobly.domain

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val state: String = "",
    val city: String = "",
    val neighborhood: String = "",
    val street: String = "",
    val number: String  = "",
    val complement: String = "",
    val cep: String = ""
)