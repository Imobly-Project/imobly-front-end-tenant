package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.domain.Lease
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

class LeaseHttpClient(val httpClient: HttpClient) {

    val baseUrl = "/locacoes"

    suspend fun searchAllByTitleOrName(titleOrName: String? = ""): List<Lease> {
        val response = httpClient.get("$baseUrl/encontrartodos?nomeoutitulo=$titleOrName") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun findById(id: String): Lease {
        val response = httpClient.get("$baseUrl/encontrarporid/$id") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }
}