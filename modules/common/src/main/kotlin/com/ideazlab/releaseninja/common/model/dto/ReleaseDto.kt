package com.ideazlab.releaseninja.common.model.dto

import com.ideazlab.releaseninja.common.model.DataDto
import java.time.LocalDate

data class ReleaseDto(
    override var id: String? = "",
    val version: String,
    var artifacts: MutableList<Artifact>? = mutableListOf<Artifact>(),
    val date: LocalDate,
    val project: ProjectDto? = null,
    val notes: String? = "",
    var snapshot:Boolean = false
): DataDto()
