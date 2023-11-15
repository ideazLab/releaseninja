package com.ideazlab.releaseninja.common.utils

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client


@Client()
interface ErrorRedirect {
    @Get("/errors/notFound")
    fun notFoundRedirect(): String?
}