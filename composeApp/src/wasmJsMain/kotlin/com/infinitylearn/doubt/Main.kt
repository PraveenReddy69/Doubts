package com.infinitylearn.doubt

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.infinitylearn.doubt.networking.CreateWebHTTPClient
import com.infinitylearn.doubt.networking.DoubtsAPIClient
import com.infinitylearn.doubt.networking.createHttpClient
import io.ktor.client.engine.js.Js
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        App(client = remember {
            DoubtsAPIClient(CreateWebHTTPClient())
        })
    }
}