package com.ideazlab.releaseninja.amt.nexus

import com.ideazlab.releaseninja.amt.nexus.api.NexusStatus
import com.ideazlab.releaseninja.amt.nexus.dto.NexusAMTDataDto
import com.ideazlab.releaseninja.amt.nexus.model.NexusAMTData
import com.ideazlab.releaseninja.amt.nexus.repo.NexusATMRepository
import com.ideazlab.releaseninja.common.annotations.Service
import com.ideazlab.releaseninja.common.model.dto.Artifact
import com.ideazlab.releaseninja.common.plugins.PluginStatus
import com.ideazlab.releaseninja.common.utils.ArtifactType
import kong.unirest.json.JSONArray
import kong.unirest.json.JSONObject
import org.apache.commons.lang3.StringUtils
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

@Service
class NexusService(
    private val repository: NexusATMRepository,
    private val nexusStatus: NexusStatus
) {
    val logger = LoggerFactory.getLogger(this::class.java)
    suspend fun existsById(id: String) = repository.existsById(ObjectId(id))
    suspend fun findById(id: String) = repository.findById(ObjectId(id))

    @Throws(Exception::class)
    suspend fun create(entity: NexusAMTDataDto): CreateNexusResponse {
        if (!repository.existsByNameAndPlugin(name = entity.name, plugin = entity.plugin)) {
            if (nexusStatus.isValid(entity)) {
                val amt = NexusAMTData(
                    name = entity.name, plugin = entity.plugin, security = entity.security,
                    settings = entity.settings
                )
                amt.update(entity)
                amt.status = PluginStatus.VERIFIED
                repository.save(amt)
                return CreateNexusResponse(success = true, message = "Nexus Server successfully created")
            } else
                return CreateNexusResponse(success = false, message = "Nexus Server security invalid")
        }
        return CreateNexusResponse(success = false, message = "Nexus Server with that name already exist")
    }

    suspend fun validate(entity: NexusAMTDataDto, artifact: Artifact): Artifact {
        var contentType: String = ""
        when (artifact.type) {
            ArtifactType.EXE -> contentType = ""
            ArtifactType.JAR -> contentType = "application/java-archive"
            ArtifactType.ZIP -> contentType = "application/zip"
            ArtifactType.TAR -> contentType = "application/zip"
        }
        val response = nexusStatus.search(entity, artifact)
        if (response != null) {
            try {
                var assets: JSONArray =
                    response.body.`object`.getJSONArray("items").getJSONObject(0).getJSONArray("assets")
                var asset: JSONObject? = null

                for (i in 0..assets.length() - 1) {
                    if (assets.getJSONObject(i).get("contentType").toString() == contentType)
                        asset = assets.getJSONObject(i)
                }
                if (asset != null) {
                    val extension = asset.getJSONObject("maven2").get("extension").toString()
                    val group = asset.getJSONObject("maven2").get("groupId").toString()
                    val name = asset.getJSONObject("maven2").get("artifactId").toString()
                    val version = asset.getJSONObject("maven2").get("version").toString()
                    if (StringUtils.endsWithIgnoreCase(extension, artifact.type.name) &&
                        StringUtils.endsWithIgnoreCase(group, artifact.group) &&
                        StringUtils.endsWithIgnoreCase(name, artifact.name) &&
                        StringUtils.endsWithIgnoreCase(version, artifact.version)
                    ) {
                        println(asset.get("downloadUrl"))
                        artifact.validated = true
                        artifact.downloadUrl = asset.get("downloadUrl").toString()
                    }
                }
            } catch (e: Exception) {
                logger.error(e.message)
            }


        }
        return artifact;
    }

    suspend fun list() = repository.findByPlugin(Nexus.name)

}