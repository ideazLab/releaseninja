package com.ideazlab.releaseninja.common.model.repo

import com.ideazlab.releaseninja.common.annotations.RNRepository
import com.ideazlab.releaseninja.common.model.domain.Project
import io.micronaut.context.annotation.Executable
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Join
import io.micronaut.data.repository.PageableRepository
import org.bson.types.ObjectId
import java.util.*

@RNRepository
interface ProjectRepository: PageableRepository<Project, ObjectId> {
    @Executable
    fun existsByName(name: String): Boolean

    @Join("releases")
    @Join("currentRelease")
    @Join("currentSnapshot")
    override fun findById(id: ObjectId): Optional<Project>

    @Join("releases")
    @Join("currentRelease")
    @Join("currentSnapshot")
    override fun findAll(): @NonNull MutableList<Project>?
}