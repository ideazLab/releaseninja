package com.ideazlab.releaseninja.amt.nexus

import com.ideazlab.releaseninja.common.annotations.Plugin
import com.ideazlab.releaseninja.common.model.dto.Artifact
import com.ideazlab.releaseninja.common.plugins.amt.AMTDataDto
import com.ideazlab.releaseninja.common.plugins.amt.AMTPlugin

@Plugin
class NexusPlugin(
    private val service: NexusService
) : AMTPlugin {
    private val name: String = Nexus.name
    private val displayName = Nexus.displayName
    private val isEnabled: Boolean = true
    private val modalId: String = Nexus.modalId
    override suspend fun list(): List<AMTDataDto> {
        return service.list().map {
            it.getDTO()
        } as List<AMTDataDto>
    }

    override suspend fun validateArtifact(id: String, artifact: Artifact): Artifact {
        if (service.existsById(id)) {
            return service.validate(service.findById(id).get().getDTO(), artifact)
        }
        return artifact
    }

    override fun getName() = name

    override fun enabled() = isEnabled
    override fun getModalId() = modalId
    override fun getDisplayName() = displayName


}