package com.ideazlab.releaseninja.controller

import com.ideazlab.releaseninja.common.api.request.project.UpdateProjectGeneralInfo
import com.ideazlab.releaseninja.common.api.response.BaseResponse
import com.ideazlab.releaseninja.common.model.dto.ProjectDto
import com.ideazlab.releaseninja.common.plugins.PluginManager
import com.ideazlab.releaseninja.common.service.ProjectService
import io.micronaut.http.*
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import kotlinx.coroutines.runBlocking

@Controller("/projects")
class ProjectController(
    private val projectService: ProjectService,
    private val pluginManager: PluginManager
){
    @Get
    @View("projects/home")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun index(): Map<String, Any> {
        val result = HashMap<String, Any>()
        runBlocking {
            result.put("projects",projectService.list())
        }
        return result
    }
    @Post
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun create(@Body o: ProjectDto): Map<String, Any> {
        val result = HashMap<String, Any>()
        return result
    }

    @Get("/{id}")
    @View("projects/project")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun read(@PathVariable id: String): HttpResponse<Map<String, Any>> {
        val result = HashMap<String, Any>()
        runBlocking {
            projectService.read(id).ifPresent {
                result.put("project", it)
            }
        }
        return HttpResponse.ok(result)
    }

    @Delete
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun delete(id: String): HttpResponse<out BaseResponse> {
        TODO("Not yet implemented")
        println(id)
    }

    @Post("/update-general-info")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun updateGeneralInfo(@Body o: UpdateProjectGeneralInfo): HttpResponse<out BaseResponse> {
        var result: BaseResponse
        runBlocking {
            result = projectService.update(o)
        }
        return HttpResponse.ok(result)
    }


    suspend fun list(): HttpResponse<BaseResponse> {
        TODO("Not yet implemented")
    }
}