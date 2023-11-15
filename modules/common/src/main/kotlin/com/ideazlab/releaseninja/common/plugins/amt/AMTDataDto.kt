package com.ideazlab.releaseninja.common.plugins.amt

import com.ideazlab.releaseninja.common.annotations.DTO
import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.plugins.PluginStatus

@DTO
abstract class AMTDataDto : DataDto() {
    open lateinit var name: String
    open lateinit var plugin: String
    open lateinit var status: PluginStatus
}