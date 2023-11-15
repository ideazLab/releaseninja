package com.ideazlab.releaseninja.common.model.domain

import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.model.DataObj
import com.ideazlab.releaseninja.common.plugins.amt.AMTPlugin
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@MappedEntity(value = "amt")
@Serdeable
@Introspected
data class ArtifactManager(
    val name: String,
    val scmPlugin: AMTPlugin
) : DataObj() {
    override fun update(update: DataDto) {
        TODO("Not yet implemented")
    }

    override fun getDTO(): DataDto {
        TODO("Not yet implemented")
    }
}