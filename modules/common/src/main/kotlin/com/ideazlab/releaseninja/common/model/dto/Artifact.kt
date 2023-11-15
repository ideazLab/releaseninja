package com.ideazlab.releaseninja.common.model.dto

import com.ideazlab.releaseninja.common.annotations.DTO
import com.ideazlab.releaseninja.common.utils.ArtifactType
import com.ideazlab.releaseninja.common.utils.ArtifactVisibility

@DTO
data class Artifact(
    var artifactId: String? = null,
    var name: String,
    var group: String,
    var version: String,
    var type: ArtifactType = ArtifactType.JAR,
    var visibility: ArtifactVisibility = ArtifactVisibility.PUBLIC,
    var snapshot:Boolean = false,
    var validated: Boolean? = false,
    var downloadUrl: String? = ""
) {
    fun update(update: Artifact) {
        name = update.name
        group = update.group
        version = update.version
        type = update.type
        visibility = update.visibility
        validated = update.validated
        downloadUrl = update.downloadUrl
    }
}