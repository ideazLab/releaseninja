package com.ideazlab.releaseninja.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View

@Controller()
@Secured(SecurityRule.IS_ANONYMOUS)
class Dashboard {
    @Get(uri = "/")
    @View("index")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun index(): Map<String, Any> = mutableMapOf()
}
