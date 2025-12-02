package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.domain.Appointment
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AppointmentHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/agendamentos"

    suspend fun create(appointment: Appointment): ResponseMessage {
        val response = httpClient.post("$baseUrl/criar") {
            contentType(ContentType.Application.Json)
            setBody(appointment)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}