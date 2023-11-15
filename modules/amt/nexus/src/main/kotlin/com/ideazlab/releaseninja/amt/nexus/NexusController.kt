package com.ideazlab.releaseninja.amt.nexus

import com.ideazlab.releaseninja.amt.nexus.dto.NexusAMTDataDto
import com.ideazlab.releaseninja.amt.nexus.model.NexusSecurity
import com.ideazlab.releaseninja.amt.nexus.model.NexusSettings
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import kotlinx.coroutines.runBlocking

@Controller("/plugins/amt/nexus")
class NexusController(
    private val service: NexusService
) {
    @Post
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun create(@Body request: CreateNexusRequest): CreateNexusResponse {
        val security = NexusSecurity(url = request.url, username = request.username, password = request.password)
        val settings = NexusSettings()
        val amt = NexusAMTDataDto(name = request.name, plugin = Nexus.name, security = security, settings = settings)
        var response: CreateNexusResponse
        runBlocking {
            try {
                response = service.create(amt)
            } catch (e: Exception) {
                response = CreateNexusResponse(message = e.message!!, success = false)
            }
        }
        return response
    }
}