package com.ideazlab.releaseninja.amt.artifactory

import com.ideazlab.releaseninja.common.model.dto.Artifact
import com.ideazlab.releaseninja.common.plugins.amt.AMTPlugin
import jakarta.inject.Singleton

@Singleton
class ArtifactoryPlugin : AMTPlugin {
    override suspend fun validateArtifact(id: String, artifact: Artifact): Artifact {
        TODO("Not yet implemented")
    }


    override fun getName() = "Artifactory"

    override fun enabled() = false
    override fun getModalId(): String {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): String {
        TODO("Not yet implemented")
    }

}