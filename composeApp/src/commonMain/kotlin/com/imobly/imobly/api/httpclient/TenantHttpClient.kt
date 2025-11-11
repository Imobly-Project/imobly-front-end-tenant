package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.domain.Tenant
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json


class TenantHttpClient (val httpClient: HttpClient) {

    val baseUrl = "/locatarios"

    val json = Json {
        encodeDefaults = true
        explicitNulls = false
    }

    suspend fun searchAll(): List<Tenant> {
        val response = httpClient.get("$baseUrl/encontrartodos") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }
    suspend fun update(id: String, tenant : Tenant, image: GalleryPhotoResult?): ResponseMessage {
        val response = httpClient.put("$baseUrl/atualizar/$id") {
            header("Authorization", "Bearer $TOKEN")
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

    suspend fun delete(id: String): ResponseMessage {
        val response = httpClient.delete("$baseUrl/deletar/$id") {
            header("Authorization", "Bearer $TOKEN")
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}