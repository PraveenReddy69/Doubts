package com.infinitylearn.doubt.networking

import com.infinitylearn.doubt.BuildConfig

actual fun getDoubtsBaseURL(): String {
    return BuildConfig.AIMENTOR_BASE_URL
}