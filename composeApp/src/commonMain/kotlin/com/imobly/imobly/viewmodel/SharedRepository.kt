package com.imobly.imobly.viewmodel

import com.imobly.imobly.domain.Payment
import com.imobly.imobly.domain.Property

object SharedRepository {
    var property: Property? = null
    var payment: Payment? = null
}