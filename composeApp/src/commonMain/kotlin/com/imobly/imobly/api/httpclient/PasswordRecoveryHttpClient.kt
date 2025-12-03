package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResetPasswordDTO
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.api.dto.EmailDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class PasswordRecoveryHttpClient(val httpClient: HttpClient) {

    private val baseUrl = "/redefinirsenha/locatario"


    suspend fun requestCode(dto : EmailDTO): ResponseMessage {
        val response = httpClient.post("$baseUrl/solicitarcodigo") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun validateCode(email: String, code: String): ResponseMessage {
        val response = httpClient.get("$baseUrl/validartoken/$email/$code")
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun resetPassword(dto : ResetPasswordDTO): ResponseMessage {
        val response = httpClient.patch("$baseUrl/criarnovasenha") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}
