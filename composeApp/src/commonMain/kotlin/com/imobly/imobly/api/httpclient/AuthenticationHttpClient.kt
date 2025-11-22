package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.api.dto.TokenDTO
import com.imobly.imobly.domain.Auth
import com.imobly.imobly.domain.Tenant
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

class AuthenticationHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/autenticacoes/locatario"

    val json = Json {
        encodeDefaults = true
        explicitNulls = false
    }

    suspend fun signIn(auth: Auth): ResponseMessage {
        val response = httpClient.post("$baseUrl/logar") {
            contentType(ContentType.Application.Json)
            setBody(auth)
        }
        if (response.status.isSuccess()) {
            TOKEN = response.body<TokenDTO>().token
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun signUp(tenant: Tenant, image: GalleryPhotoResult?): ResponseMessage {
        val response = httpClient.post("$baseUrl/cadastrar") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append(
                            key = "tenant",
                            value = json.encodeToString(tenant),
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "application/json")
                            }
                        )
                        if (image != null) {
                            append(
                                key = "file",
                                value = image.loadBytes(),
                                headers = Headers.build {
                                    append(HttpHeaders.ContentDisposition, "filename=\"${image.fileName}\"")
                                    append(HttpHeaders.ContentType, "multipart/form-data")
                                }
                            )
                        }
                    }
                ))
        }

        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}