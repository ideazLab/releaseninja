package com.ideazlab.releaseninja.controller

import com.ideazlab.releaseninja.common.api.request.project.*
import com.ideazlab.releaseninja.common.api.response.BaseResponse
import com.ideazlab.releaseninja.common.model.dto.Artifact
import com.ideazlab.releaseninja.common.model.dto.ReleaseDto
import com.ideazlab.releaseninja.common.plugins.PluginManager
import com.ideazlab.releaseninja.common.service.ProjectService
import com.ideazlab.releaseninja.common.service.ReleaseService
import com.ideazlab.releaseninja.common.utils.ArtifactType
import com.ideazlab.releaseninja.common.utils.ArtifactVisibility
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import kotlinx.coroutines.runBlocking
import org.apache.commons.lang3.StringUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Controller("/releases")
@Secured(SecurityRule.IS_AUTHENTICATED)
class ReleaseController(
    private val service: ReleaseService,
    private val projectService: ProjectService,
    private val pluginManager: PluginManager
) {
    @Get
    @View("releases/home")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun index(): Map<String, Any> {
        val result = HashMap<String, Any>()
        runBlocking {
            result.put("releases", service.list())
        }
        return result
    }

    @Get("/{id}")
    @View("releases/release")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun read(@PathVariable id: String): HttpResponse<Map<String, Any>> {
        val result = HashMap<String, Any>()
        runBlocking {
            service.read(id).ifPresent {
                result.put("release", it)
            }
        }
        return HttpResponse.ok(result)
    }

    @Post("/create-release")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun createRelease(@Body o: CreateProjectRelease): HttpResponse<out BaseResponse> {
        val result = BaseResponse(false, "Error creating Release ${o.version}")
        o.id?.let { id ->
            if (projectService.existById(id)) {
                projectService.read(id).ifPresent {
                    runBlocking {
                        try {
                            it.amt?.let {
                                val amt = pluginManager.getAMT(it).get()
                                val plugin = pluginManager.getAMRPlugin(amt.plugin)
                                o.artifacts?.forEach {
                                    if (it.artifactId == null)
                                        it.artifactId = UUID.randomUUID().toString()
                                    amt.id?.let { id ->
                                        it.update(plugin.validateArtifact(id, it))
                                    }
                                }
                            }
                            service.create(
                                ReleaseDto(
                                    version = o.version, artifacts = o.artifacts,
                                    date = LocalDate.parse(o.date, DateTimeFormatter.ISO_LOCAL_DATE), project = it
                                )
                            )
                            result.success = true
                            result.message = "Success"
                        } catch (e: Exception) {
                            result.message = e.message!!
                        }
                    }
                }
            } else
                result.message = "Project Does not exist"
        }
        return HttpResponse.ok(result)
    }

    @Post("/update-current-release")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun updateCurrentRelease(@Body o: UpdateCurrentRelease): HttpResponse<out BaseResponse> =
        HttpResponse.ok(service.updateCurrentRelease(o))

    @Post("/artifact/update")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun updateArtifact(@Body o: UpdateArtifact): HttpResponse<out BaseResponse> {
        var result = BaseResponse()
        if (StringUtils.isNotBlank(o.id) && service.existById(o.id!!)) {
            service.read(o.id!!).ifPresent { dto ->
                var artifact = dto.artifacts?.filter {
                    it.artifactId == o.artifactId
                }?.first()
                if (artifact != null) {
                    artifact.name = o.name
                    artifact.group = o.group
                    artifact.version = o.version
                    artifact.type = ArtifactType.valueOf(o.type)
                    artifact.visibility = ArtifactVisibility.valueOf(o.visibility)
                    dto.project?.amt?.let {
                        runBlocking {
                            val amt = pluginManager.getAMT(it).get()
                            val plugin = pluginManager.getAMRPlugin(amt.plugin)
                            amt.id?.let { it1 ->
                                dto.artifacts!![dto.artifacts?.indexOf(artifact)!!] =
                                    plugin.validateArtifact(it1, artifact)
                            }

                        }
                    }
                }
                runBlocking { service.update(dto) }
            }
        } else {
            result.success = false
            result.message = "Error updating Artifact ${o.name}"
        }
        return HttpResponse.ok(result)
    }

    @Post("/artifact/delete")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun deleteArtifact(@Body o: DeleteArtifact): HttpResponse<out BaseResponse> {
        var result = BaseResponse()
        if (StringUtils.isNotBlank(o.id) && service.existById(o.id!!)) {
            service.read(o.id!!).ifPresent { dto ->
                dto.artifacts = dto.artifacts?.filter { it.artifactId != o.artifactId } as MutableList<Artifact>
                runBlocking { service.update(dto) }
            }
        } else {
            result.success = false
            result.message = "Error deleting Artifact ${o.name}"
        }
        return HttpResponse.ok(result)
    }

    @Get("/artifact/validate/{releaseId}/{artifactId}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun validateArtifact(
        @PathVariable releaseId: String,
        @PathVariable artifactId: String
    ): HttpResponse<out BaseResponse> {
        var result = BaseResponse()

        if (service.existById(releaseId)) {
            val release = service.read(releaseId).get()
            release.project?.amt?.let {
                val artifact = release.artifacts?.first { it.artifactId == artifactId }
                val amt = pluginManager.getAMT(it).get()
                val plugin = pluginManager.getAMRPlugin(amt.plugin)
                if (artifact != null) {
                    amt.id?.let { it1 ->
                        release.artifacts!![release.artifacts?.indexOf(artifact)!!] =
                            plugin.validateArtifact(it1, artifact)
                    }
                }
            }
            service.update(release)
        }
        return HttpResponse.ok(result)
    }

    @Post("/artifact/create")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    suspend fun createArtifact(@Body o: CreateArtifact): HttpResponse<out BaseResponse> {
        var result = BaseResponse()
        if (StringUtils.isNotBlank(o.id) && service.existById(o.id!!)) {
            service.read(o.id!!).ifPresent { dto ->
                runBlocking {
                    if (dto.artifacts?.filter {
                            it.group == o.group && it.name == o.name && it.version == o.version && it.type.name == o.type
                        }?.size!! == 0) {
                        dto.project?.amt?.let {
                            val amt = pluginManager.getAMT(it).get()
                            val plugin = pluginManager.getAMRPlugin(amt.plugin)
                            var artifact = plugin.validateArtifact(
                                it, Artifact(
                                    artifactId = UUID.randomUUID().toString(),
                                    name = o.name,
                                    group = o.group,
                                    version = o.version,
                                    type = ArtifactType.valueOf(o.type),
                                    visibility = ArtifactVisibility.valueOf(o.visibility)
                                )
                            )
                            if (dto.artifacts == null)
                                dto.artifacts = arrayListOf(artifact)
                            else
                                dto.artifacts!!.add(artifact)
                            service.update(dto)
                        }
                    } else {
                        result.success = false
                        result.message = "Error updating Artifact ${o.name} is a duplicate"
                    }

                }
            }
        } else {
            result.success = false
            result.message = "Error updating Artifact ${o.name}"
        }
        return HttpResponse.ok(result)
    }
}