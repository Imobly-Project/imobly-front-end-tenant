package com.imobly.imobly.api.httpclient

import com.imobly.imobly.domain.Category
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CategoryHttpClient(val httpClient: HttpClient) {

    val baseUrl = "/categorias"

    suspend fun searchAllByTitle(title: String? = ""): List<Category> =
        httpClient.get("$baseUrl/encontrartodos?titulo=$title")
            .body()
}