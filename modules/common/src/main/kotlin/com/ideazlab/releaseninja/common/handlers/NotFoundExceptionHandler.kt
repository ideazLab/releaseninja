package com.ideazlab.releaseninja.common.handlers

import com.ideazlab.releaseninja.common.exception.NotFoundException
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor
import io.micronaut.views.ViewsRenderer;
import jakarta.inject.Singleton
import java.util.*

@Produces
@Singleton
@Requirements(
    Requires(classes = [NotFoundException::class, ExceptionHandler::class])
)
class NotFoundExceptionHandler (
    private val errorResponseProcessor: ErrorResponseProcessor<Any>,
    private val viewsRenderer: ViewsRenderer<Any, Any>
) : ExceptionHandler<NotFoundException, HttpResponse<*>>{
    override fun handle(request: HttpRequest<*>?, exception: NotFoundException?): HttpResponse<*> {
        if(request!!.headers.accept().stream().anyMatch{
            it.name.contains(MediaType.TEXT_HTML)
            }){
            val result = HashMap<String, Any>()
            result.put("message",exception?.message!!)
            result.put("subMessage","Resource does not exist on this server")
            result.put("code","404!")
            result.put("description","Not Found")
            if(request.headers.get("Referrer")!=null) {
                result.put("backlink", request.headers.get("Referrer")!!)
                result.put("backlinkText", "Go back")
            }else {
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