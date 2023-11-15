package com.ideazlab.releaseninja.common.handlers

import com.ideazlab.releaseninja.common.exception.DataException
import com.ideazlab.releaseninja.common.utils.StatusUtils
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.server.exceptions.response.ErrorContext
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor
import io.micronaut.views.ViewsRenderer
import jakarta.inject.Singleton
import java.util.*

@Produces
@Singleton
@Requirements(
    Requires(classes = [DataException::class, ExceptionHandler::class])
)
class DataExceptionHandler (
    private val errorResponseProcessor: ErrorResponseProcessor<Any>,
    private val viewsRenderer: ViewsRenderer<Any, Any>
) :
    ExceptionHandler<DataException, HttpResponse<*>>{
    override fun handle(request: HttpRequest<*>?, exception: DataException?): HttpResponse<*> {
        if (StatusUtils.isHtmlRequest(request!!)) {
            val result = HashMap<String, Any>()
            result.put("message", exception?.message!!)
            result.put("subMessage", "The server cannot or will not process the request")
            result.put("code", "400!")
            result.put("description", "Malformed request / Bad Request")
            if (request.headers.get("Referrer") != null) {
                result.put("backlink", request.headers.get("Referrer")!!)
                result.put("backlinkText", "Go back")
            } else {
                result.put("backlink", "/")
                result.put("backlinkText", "Go to Dashboard")
            }
            return HttpResponse.ok(viewsRenderer.render("errors", result, request))
                .contentType(MediaType.TEXT_HTML);
        }
        return errorResponseProcessor.processResponse(
            ErrorContext.builder(request)
                .cause(exception)
                .errorMessage("No stock available")
                .build(), HttpResponse.badRequest<Any>())
    }
}