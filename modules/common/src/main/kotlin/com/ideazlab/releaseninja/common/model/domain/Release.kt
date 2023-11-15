package com.ideazlab.releaseninja.common.model.domain

import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.model.DataObj
import com.ideazlab.releaseninja.common.model.dto.Artifact
import com.ideazlab.releaseninja.common.model.dto.ReleaseDto
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDate

@MappedEntity(value = "release")
@Serdeable
@Introspected
data class Release(
    var version: String,
    var artifacts: MutableList<Artifact>? = mutableListOf<Artifact>(),
    var date: LocalDate,
    @Relation(Relation.Kind.MANY_TO_ONE)
    var project: Project?,
    var notes: String? = "",
    var snapshot:Boolean = false,
) : DataObj() {
    override fun update(update: DataDto) {
        val dto = update as ReleaseDto
        version = dto.version
        artifacts = dto.artifacts
        date = dto.date
        notes = dto.notes
    }

    override fun getDTO(): ReleaseDto {
        var dto =
            ReleaseDto(id = id?.toHexString(), version = version, artifacts = artifacts, date = date, notes = notes, snapshot = snapshot)
        project?.let {
            dto = ReleaseDto(
                id = id?.toHexString(),
                version = version,
                artifacts = artifacts,
                date = date,
                project = project?.getDTO()!!,
                notes = notes,
                snapshot = snapshot
            )
        }
        return dto
    }
}