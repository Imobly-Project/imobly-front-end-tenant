package com.imobly.imobly.api.dto

import com.imobly.imobly.domain.Telephone
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileDTO(

    val email: String = "",

    val telephones: Telephone = Telephone(),

)