package com.ideazlab.releaseninja.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/login")
class LoginController {
    @Get("/auth")
    @View("login")
    fun auth(): Map<String, Any> = mutableMapOf()
    @Get("/authFailed")
    @View("login")
    fun authFailed(): Map<String, Any> = mapOf("errors" to true)
}