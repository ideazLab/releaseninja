package com.ideazlab.releaseninja.amt.nexus.api

import com.ideazlab.releaseninja.amt.nexus.dto.NexusAMTDataDto
import com.ideazlab.releaseninja.common.annotations.NinjaAPI
import com.ideazlab.releaseninja.common.model.dto.Artifact
import com.ideazlab.releaseninja.common.utils.ArtifactVisibility
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest

@NinjaAPI()
class NexusStatus {
    private val STATUS = "/service/rest/v1/status"
    private val WRITABLE = "/service/rest/v1/status/writable"
    private val CHECK = "/service/rest/v1/status/check"
    private val SEARCH = "/service/rest/v1/search"
    fun isValid(entity: NexusAMTDataDto): Boolean {
        val response = Unirest.get("${entity.security.url}$WRITABLE")
            .basicAuth(entity.security.username, entity.security.password)
            .asEmpty()
        return response.status == 200
    }

    fun isWritable(entity: NexusAMTDataDto): Boolean {
        val response = Unirest.get("${entity.security.url}$STATUS")
            .basicAuth(entity.security.username, entity.security.password)
            .asEmpty()
        return response.status == 200
    }

    fun check(entity: NexusAMTDataDto): HttpResponse<JsonNode>? {
        val response = Unirest.get("${entity.security.url}$CHECK")
            .basicAuth(entity.security.username, entity.security.password)
            .asJson()
        return response
    }

    fun search(entity: NexusAMTDataDto, artifact: Artifact): HttpResponse<JsonNode>? {
        var repository = ""
        when(artifact.visibility){
            ArtifactVisibility.PRIVATE->{
                if(artifact.snapshot)
                    repository = entity.security.snapshotRepository
                else
                    repository = entity.security.repository
            }
            ArtifactVisibility.PUBLIC->{
                repository = "maven-public"
            }
        }


        return Unirest.get("${entity.security.url}$SEARCH")
            .basicAuth(entity.security.username, entity.security.password)
            .queryString("repository", repository)
            .queryString("group", artifact.group)
            .queryString("name", artifact.name)
            .queryString("version", artifact.version)
            .queryString("maven.extension", artifact.type.name.toString().lowercase())
            .asJson()
    }
}