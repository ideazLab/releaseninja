package com.ideazlab.releaseninja.security

import com.ideazlab.releaseninja.common.exception.AuthenticationException
import com.ideazlab.releaseninja.security.service.SecurityService
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class AuthenticationProviderUserPassword(
    private val securityService: SecurityService
) : AuthenticationProvider<HttpRequest<*>> {

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse> {
        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
            runBlocking {
                try {
                    securityService.isValidLogin(
                        authenticationRequest.identity as String,
                        authenticationRequest.secret as String
                    )
                    val user = securityService.findByUsername(authenticationRequest.identity as String).get()
                    emitter.next(AuthenticationResponse.success(user.username, listOf(user.role.name)))
                    emitter.complete()
                }catch (e: AuthenticationException){
                    emitter.error(AuthenticationResponse.exception(e.getFailure()))
                }
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}