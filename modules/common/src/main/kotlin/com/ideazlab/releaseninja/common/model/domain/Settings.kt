package com.ideazlab.releaseninja.common.model.domain

import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.model.DataObj
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@MappedEntity(value = "settings")
@Serdeable
@Introspected
data class Settings(
    val artifactManagers: List<ArtifactManager> = emptyList(),
    val sourceControlManager: List<SourceControlManager> = emptyList()
):DataObj() {
    override fun update(update: DataDto) {
        TODO("Not yet implemented")
    }

    override fun getDTO(): DataDto {
        TODO("Not yet implemented")
    }
}