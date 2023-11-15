package com.ideazlab.releaseninja.common.plugins.amt

import com.ideazlab.releaseninja.common.model.dto.Artifact
import com.ideazlab.releaseninja.common.plugins.BasePlugin

interface AMTPlugin : BasePlugin {
    suspend fun list(): List<AMTDataDto> = emptyList()
    suspend fun validateArtifact(id: String, artifact: Artifact): Artifact
}