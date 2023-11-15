package com.ideazlab.releaseninja.amt.nexus.dto

import com.ideazlab.releaseninja.amt.nexus.model.NexusSecurity
import com.ideazlab.releaseninja.amt.nexus.model.NexusSettings
import com.ideazlab.releaseninja.common.annotations.DTO
import com.ideazlab.releaseninja.common.plugins.PluginStatus
import com.ideazlab.releaseninja.common.plugins.amt.AMTDataDto

@DTO
data class NexusAMTDataDto(
    override var name: String,
    override var plugin: String,
    var security: NexusSecurity,
    var settings: NexusSettings? = null,
    override var status: PluginStatus = PluginStatus.PENDING
) : AMTDataDto()