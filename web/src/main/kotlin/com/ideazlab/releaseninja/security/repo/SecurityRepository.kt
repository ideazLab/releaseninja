package com.ideazlab.releaseninja.security.repo

import com.ideazlab.releaseninja.common.annotations.RNRepository
import com.ideazlab.releaseninja.security.domain.Security
import io.micronaut.context.annotation.Executable
import io.micronaut.data.repository.PageableRepository
import org.bson.types.ObjectId
import java.util.*

@RNRepository
interface SecurityRepository: PageableRepository<Security, ObjectId> {
    @Executable
    fun existsByUsername(username:String):Boolean
    @Executable
    fun findByUsername(username: String): Optional<Security>
}