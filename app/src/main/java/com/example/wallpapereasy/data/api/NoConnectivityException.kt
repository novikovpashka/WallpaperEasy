package com.example.wallpapereasy.data.api

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No internet connection"
}