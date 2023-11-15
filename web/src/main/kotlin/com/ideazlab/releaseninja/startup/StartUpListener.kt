package com.ideazlab.releaseninja.startup

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ideazlab.releaseninja.common.model.dto.ProjectDto
import com.ideazlab.releaseninja.common.model.dto.SecurityDto
import com.ideazlab.releaseninja.common.service.ProjectService
import com.ideazlab.releaseninja.security.service.SecurityService
import io.micronaut.discovery.event.ServiceReadyEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream

@Singleton
class StartUpListener(
    private val mapper: ObjectMapper,
    private val securityService: SecurityService,
    private val projectService: ProjectService
){
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    @EventListener
    fun startUp(event: ServiceReadyEvent){
        checkBaseUser();
        checkProjects()
    }
    private fun checkBaseUser(){
        val securities: List<SecurityDto> = mapper.readValue(this::class.java.getResourceAsStream("/data/user.json") as InputStream)
        securities.forEach {
            runBlocking {
                logger.debug(it.username)
                if(securityService.existByUsername(it.username)){
                    securityService.updatePassword(it)
                }else
                    securityService.create(it)
            }
        }
    }
    private fun checkProjects(){
        val projects: List<ProjectDto> = mapper.readValue(this::class.java.getResourceAsStream("/data/projects.json") as InputStream)
        projects.forEach {
            runBlocking {
                logger.debug(it.name)
                if (!projectService.existByName(it.name))
                    projectService.create(it)

            }
        }
    }
}