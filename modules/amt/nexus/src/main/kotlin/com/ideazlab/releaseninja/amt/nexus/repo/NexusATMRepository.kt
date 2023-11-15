package com.ideazlab.releaseninja.amt.nexus.repo

import com.ideazlab.releaseninja.amt.nexus.model.NexusAMTData
import com.ideazlab.releaseninja.common.annotations.RNRepository
import io.micronaut.context.annotation.Executable
import io.micronaut.data.repository.PageableRepository
import org.bson.types.ObjectId
import java.util.*

@RNRepository
interface NexusATMRepository : PageableRepository<NexusAMTData, ObjectId> {
    @Executable
    fun existsByNameAndPlugin(name: String, plugin: String): Boolean

    @Executable
    fun findByNameAndPlugin(name: String, plugin: String): Optional<NexusAMTData>

    fun findByPlugin(plugin: String): List<NexusAMTData>
}