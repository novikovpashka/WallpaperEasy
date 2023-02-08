package com.example.wallpapereasy.extensions

import java.net.URLDecoder
import java.net.URLEncoder

fun String.encodeAsUrl() : String {
    return URLEncoder.encode(this, "utf-8")
}

fun String.decodeUrl() : String {
    return URLDecoder.decode(this, "utf-8")
}