package com.ideazlab.releaseninja.controller

import com.ideazlab.releaseninja.common.utils.StatusUtils
import io.micronaut.http.*
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.ViewsRenderer;

@Controller("/notfound")
@Secured(SecurityRule.IS_ANONYMOUS)
class NotFoundController(
    private var viewsRenderer: ViewsRenderer<Any, Any>?
) {
    @Error(status = HttpStatus.NOT_FOUND, global = true) // <3>
    fun notFound(request: HttpRequest<Any>): HttpResponse<*> {
        if (StatusUtils.isHtmlRequest(request)) {
            val result = HashMap<String, Any>()
            result["message"] = "You don't have permission to access this resource"
            result["subMessage"] = "Back to Login of Release Ninja"
            result["code"] = "404!"
            result["description"] = "Forbidden"
            result["backlink"] = "login/auth"
            result["backlinkText"] = "back to login"
            return HttpResponse.ok(viewsRenderer!!.render("errors", result, request)).contentType(MediaType.TEXT_HTML);
        }
        val error: JsonError = JsonError("Page Not Found").link(Link.SELF, Link.of(request.getUri()))
        return HttpResponse.notFound<JsonError?>().body(error) // <5>
    }

    @Error(status = HttpStatus.INTERNAL_SERVER_ERROR, global = true) // <3>
    fun serverError(request: HttpRequest<Any>): HttpResponse<*> {
        if (StatusUtils.isHtmlRequest(request)) {
            val result = HashMap<String, Any>()
            result["message"] = "You don't have permission to access this resource"
            result["subMessage"] = "Back to Login of Release Ninja"
            result["code"] = "404!"
            result["description"] = "Forbidden"
            result["backlink"] = "login/auth"
            result["backlinkText"] = "back to login"
            return HttpResponse.ok(viewsRenderer!!.render("errors", result, request)).contentType(MediaType.TEXT_HTML);
        }
        val error: JsonError = JsonError("Page Not Found")
            .link(Link.SELF, Link.of(request.getUri()))
        return HttpResponse.notFound < JsonError ? > ().body(error) // <5>
    }
}