package com.ideazlab.releaseninja.common.model.repo

import com.ideazlab.releaseninja.common.annotations.RNRepository
import com.ideazlab.releaseninja.common.model.domain.Release
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Join
import io.micronaut.data.repository.PageableRepository
import org.bson.types.ObjectId
import java.util.*

@RNRepository
interface ReleaseRepository : PageableRepository<Release, ObjectId> {
    @Join("project")
    override fun findById(id: ObjectId): Optional<Release>

    @Join("project")
    override fun findAll(): @NonNull MutableList<Release>?


}