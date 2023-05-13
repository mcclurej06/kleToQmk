package com.github.mcclurej06.kletoqmk

import kotlin.js.Promise

@JsModule("axios")
@JsNonModule
external val axios: Axios

external class Axios {
    fun get(url: String): Promise<dynamic>
}