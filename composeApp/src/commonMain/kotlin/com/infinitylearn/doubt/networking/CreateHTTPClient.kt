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

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    // Print cURL request to the console
                    println("DoubtsCurl12----> " +message)
                    if (message.contains("Request:") || message.contains("Response:")) {
                        println("DoubtsCurl----> " + formatCurlCommand(message))
                    }
                }
            }
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

// Function to format the cURL command
private fun formatCurlCommand(logMessage: String): String {
    val requestLine = logMessage.substringAfter("Request: ")
    val method = HttpMethod.parse(requestLine.substringBefore(' ')).value
    val url = requestLine.substringAfter("Url: ").substringBefore('\n').trim()

    val headers = logMessage.substringAfter("Headers: ").substringBefore("Body:").trim()
    val body = logMessage.substringAfter("Body:").trim()

    val curlCommand = StringBuilder("curl -X $method '$url'")

    // Add headers
    headers.split('\n').forEach { header ->
        val headerParts = header.split(':', limit = 2)
        if (headerParts.size == 2) {
            curlCommand.append(" -H '${headerParts[0].trim()}: ${headerParts[1].trim()}'")
        }
    }

    // Add body if it exists
    if (body.isNotEmpty()) {
        curlCommand.append(" -d '$body'")
    }

    return curlCommand.toString()
}