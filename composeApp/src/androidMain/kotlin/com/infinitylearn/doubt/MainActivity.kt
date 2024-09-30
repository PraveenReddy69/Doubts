package com.infinitylearn.doubt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.infinitylearn.doubt.networking.DoubtsAPIClient
import com.infinitylearn.doubt.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(client = remember {
                DoubtsAPIClient(createHttpClient(OkHttp.create()))
            })
        }
    }
}

