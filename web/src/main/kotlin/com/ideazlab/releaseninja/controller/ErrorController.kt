package com.ideazlab.releaseninja.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/errors")
class ErrorController {

    @Get("/forbidden")
    @View("errors")
    fun forbidden(@Header("Referer") referer: String): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["message"] = "You don't have permission to access this resource"
        result["subMessage"] = "Back to Login of Release Ninja"
        result["code"] = "403!"
        result["description"] = "Forbidden"
        result["backlink"] = referer
        result["backlinkText"] = "back to login"
        return result
    }
}
