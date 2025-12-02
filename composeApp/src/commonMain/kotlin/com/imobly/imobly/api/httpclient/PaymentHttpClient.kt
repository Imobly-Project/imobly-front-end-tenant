package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.PaymentStatusDTO
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.domain.Payment
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class PaymentHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/pagamentos"

    suspend fun searchByLeaseId(id: String): Payment {
        val response = httpClient.get("$baseUrl/encontrarporlocacaoid/$id") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun updateStatus(idPayment: String, idInstallment: String, newStatus: PaymentStatusDTO): ResponseMessage {
        val response = httpClient.patch("$baseUrl/atualizarstatus/${idPayment}/${idInstallment}") {
            header("Authorization", "Bearer $TOKEN")
            contentType(ContentType.Application.Json)
            setBody(newStatus)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}