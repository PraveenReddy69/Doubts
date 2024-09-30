package com.infinitylearn.doubt

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.infinitylearn.doubt.networking.DoubtsAPIClient
import com.infinitylearn.doubt.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Doubts",
    ) {
        App(client = remember {
            DoubtsAPIClient(createHttpClient(OkHttp.create()))
        })
    }
}