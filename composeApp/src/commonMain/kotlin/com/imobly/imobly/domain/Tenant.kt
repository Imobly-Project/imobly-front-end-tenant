package com.imobly.imobly.domain

import com.imobly.imobly.domain.enums.MaritalStatusEnum
import kotlinx.serialization.Serializable


@Serializable
data class Tenant(
    val id: String? = null,
    val pathImage: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String ="",
    val telephones: Telephone = Telephone(),
    val rg: String = "",
    val cpf: String = "",
    val job: String = "",
    val nationality: String = "",
    val maritalStatus: MaritalStatusEnum = MaritalStatusEnum.SINGLE,
    val birthDate: String = "",
    val address: Address = Address()
)
