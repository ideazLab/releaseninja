package com.ideazlab.releaseninja.common.utils

import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType

object StatusUtils {
    fun isHtmlRequest(request: HttpRequest<*>) = request.headers.accept().stream()
        .anyMatch { it.name.contains(MediaType.TEXT_HTML) }
}