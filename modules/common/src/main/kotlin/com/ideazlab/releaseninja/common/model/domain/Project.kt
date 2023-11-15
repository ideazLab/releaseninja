package com.ideazlab.releaseninja.common.model.domain

import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.model.DataObj
import com.ideazlab.releaseninja.common.model.dto.ProjectDto
import com.ideazlab.releaseninja.common.utils.Status
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable
import org.bson.types.ObjectId

@MappedEntity(value = "project")
@Serdeable
@Introspected
data class Project(
    var name: String,
    var description: String,
    var amt: ObjectId? = null,
    var status: Status? = Status.LIVE,
    @Relation(Relation.Kind.ONE_TO_ONE)
    var currentRelease: Release? = null,
    @Relation(Relation.Kind.ONE_TO_ONE)
    var currentSnapshot: Release? = null,
    @Relation(Relation.Kind.ONE_TO_MANY, mappedBy = "project")
    var releases: List<Release>? = emptyList()
)  : DataObj() {

    override fun update(update: DataDto) {
        val dto = update as ProjectDto
        name = dto.name
        description = dto.description
    }

    override fun getDTO() = ProjectDto(
        id = id!!.toHexString(),
        name = name,
        description = description,
        amt = amt?.toHexString(),
        status = status,
        currentRelease = currentRelease?.getDTO(),
        releases = releases?.map { it.getDTO() }
    )

}