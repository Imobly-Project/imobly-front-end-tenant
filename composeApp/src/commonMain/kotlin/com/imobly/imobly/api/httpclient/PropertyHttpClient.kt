package com.imobly.imobly.api.httpclient

import com.imobly.imobly.domain.Property
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PropertyHttpClient(val httpClient: HttpClient) {

    val baseUrl = "/propriedades"

    suspend fun searchAllByTitle(title: String? = ""): List<Property> =
        httpClient.get("$baseUrl/encontrartodos?titulo=$title")
            .body()

    suspend fun searchByProfileAndTitle(title: String? = ""): List<Property> =
        httpClient.get("$baseUrl/encontrarporperfil?titulo=$title")
            .body()

}