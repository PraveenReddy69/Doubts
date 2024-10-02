package com.infinitylearn.doubt.networking


import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.headers
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun CreateWebHTTPClient(): HttpClient {
    return HttpClient() {

        install(Logging) {
            level = LogLevel.ALL

        }

        install(HttpTimeout){
            requestTimeoutMillis=15000
        }

        install(ContentNegotiation) {
            json(
                json = Json {
                    prettyPrint=true
                    isLenient=true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(DefaultRequest) {
            headers["x-platform"] = "android"
            headers["x-tenant"] = "infinitylearn"
            headers["accept"] = "application/json"
            headers["content-type"] = "application/json; charset=UTF-8"
            bearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NDI4MDI2NTgsImlhdCI6MTcyNzI1MDY1OCwiREJJZCI6IjM5OTQwMzAiLCJGaXJzdE5hbWUiOiJQIiwiTGFzdE5hbWUiOiJSIiwidWlkIjoibnVsbCIsIlRlbmFudElkIjoxLCJUZW5hbnRDb2RlIjoiaW5maW5pdHlsZWFybiIsIlJvbGVOYW1lIjoiU3R1ZGVudCIsIlJvbGVJZCI6IjEiLCJ1YW1faWQiOjM5OTQwMzAsImNybl9pZCI6IkNSTlAzMDBUMDAwMDFIQzNLWE1XIn0.25Cl8X-kET5qwKhGUo-Ggbb13yqWq2AbNm1sOukwBX8")
        }
    }
}

