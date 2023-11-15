package com.ideazlab.releaseninja.amt.nexus.model

import com.ideazlab.releaseninja.amt.nexus.dto.NexusAMTDataDto
import com.ideazlab.releaseninja.common.annotations.AMTData
import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.plugins.PluginStatus
import com.ideazlab.releaseninja.common.plugins.amt.AMTDataObj

@AMTData
data class NexusAMTData(
    override var name: String,
    override var plugin: String,
    var security: NexusSecurity,
    var settings: NexusSettings?,
    override var status: PluginStatus = PluginStatus.PENDING
) : AMTDataObj() {
    override fun update(update: DataDto) {
        val dto = update as NexusAMTDataDto
        name = dto.name
        plugin = dto.plugin
        security = dto.security
        settings = dto.settings
        dto.status?.let {
            status = it
        }

    }

    override fun getDTO(): NexusAMTDataDto {
        val dto =
            NexusAMTDataDto(name = name, plugin = plugin, security = security, settings = settings, status = status)
        dto.id = id!!.toHexString()
        return dto
    }
}
