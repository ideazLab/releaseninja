package com.ideazlab.releaseninja.common.plugins.amt

import com.ideazlab.releaseninja.common.annotations.EmbededData
import com.ideazlab.releaseninja.common.model.DataObj
import com.ideazlab.releaseninja.common.plugins.PluginStatus


@EmbededData
abstract class AMTDataObj : DataObj() {
    open lateinit var name: String
    open lateinit var plugin: String
    open lateinit var status: PluginStatus
}