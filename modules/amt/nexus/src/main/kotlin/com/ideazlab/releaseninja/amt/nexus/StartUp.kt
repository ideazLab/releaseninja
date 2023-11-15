package com.ideazlab.releaseninja.amt.nexus

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ideazlab.releaseninja.amt.nexus.api.NexusStatus
import com.ideazlab.releaseninja.amt.nexus.dto.NexusAMTDataDto
import io.micronaut.discovery.event.ServiceReadyEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream

@Singleton
class StartUp(
    private val mapper: ObjectMapper,
    private val service: NexusService,
    private val nexusStatus: NexusStatus
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @EventListener
    fun startUp(event: ServiceReadyEvent) {
        loadData()
    }

    private fun loadData() {
        val nexusData: List<NexusAMTDataDto> =
            mapper.readValue(this::class.java.getResourceAsStream("/data/nexus.json") as InputStream)
        nexusData.forEach {
            runBlocking {
                service.create(it)
            }
        }
    }
}