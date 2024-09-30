package com.infinitylearn.doubt

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.infinitylearn.doubt.networking.DoubtsAPIClient
import com.infinitylearn.doubt.networking.createHttpClient
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController {
    App(client = remember {
        DoubtsAPIClient(createHttpClient(Darwin.create()))
    }) }