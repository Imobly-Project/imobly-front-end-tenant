package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.CreateReportDTO
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.domain.Report
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class ReportHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/reportacoes"

    suspend fun searchAByProfileAndTitleOrMessage(titleOrMessage: String? = ""): List<Report> {
        val response = httpClient.get("$baseUrl/encontrarporperfil?titulooumensagem=$titleOrMessage") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun createReport(response: CreateReportDTO): ResponseMessage {
        val response = httpClient.post("$baseUrl/criar") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $TOKEN")
            setBody(response)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}