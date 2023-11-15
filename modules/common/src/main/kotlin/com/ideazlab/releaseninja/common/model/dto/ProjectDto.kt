package com.ideazlab.releaseninja.common.model.dto

import com.ideazlab.releaseninja.common.annotations.DTO
import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.utils.Status

@DTO
class ProjectDto(
    override var id: String? = "",
    val name: String,
    val description: String,
    val amt: String? = "",
    val status: Status? = Status.LIVE,
    val currentRelease: ReleaseDto? = null,
    val releases: List<ReleaseDto>? = emptyList()
): DataDto()