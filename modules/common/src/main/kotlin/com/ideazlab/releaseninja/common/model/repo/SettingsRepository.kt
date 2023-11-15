package com.ideazlab.releaseninja.common.model.repo

import com.ideazlab.releaseninja.common.annotations.RNRepository
import com.ideazlab.releaseninja.common.model.domain.Settings
import io.micronaut.data.repository.PageableRepository
import org.bson.types.ObjectId

@RNRepository
interface SettingsRepository : PageableRepository<Settings, ObjectId> {
//    @Executable
//    fun existsByName(name: String): Boolean
}