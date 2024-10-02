package com.infinitylearn.doubt.mainscreenui.model


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class UIConfig(
    val title: TitleConfig? = null,
    val cameraSection: CameraSection? = null,
    val textSection: TextSection? = null,
    val layoutMode: String? = "layout_1",
    val layoutType: Map<String, List<String>>? = null
)

@Serializable
data class TitleConfig(
    val text: String? = "",
    val color: String? = "",
    val fontSize: Int? = 16,
    val fontWeight: String? = "Normal",
    val strokeColor: String? = ""
)

@Serializable
data class CameraSection(
    val image: String? = "", val buttonText: String? = ""
)

@Serializable
data class TextSection(
    val buttonText: String? = ""
)


// Set up your HTTP client
val client = HttpClient {
    install(Logging) {
        level = LogLevel.ALL
    }

    install(ContentNegotiation) {
        json(Json { isLenient = true; ignoreUnknownKeys = true })
    }
}


suspend fun fetchFirebaseData(): UIConfig {
    val firebaseUrl = "https://doubts-default-rtdb.firebaseio.com/cameraTextUi/uiConfig.json"
    return client.get(firebaseUrl).body()
}

suspend fun observeCameraTextUI(): UIConfig {
    while (true) {
        try {
            val uiConfig: UIConfig = fetchFirebaseData()
            println("------------------->"+uiConfig.title?.text)
            return uiConfig
            // Handle UI update or logic with uiConfig
        } catch (e: Exception) {

            println("Error fetching Firebase data: ${e.message}")
        }

        // Polling interval (e.g., every 10 seconds)
        delay(10000)
    }
}
