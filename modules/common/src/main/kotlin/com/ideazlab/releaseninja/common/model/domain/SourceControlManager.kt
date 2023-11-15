package com.ideazlab.releaseninja.common.model.domain

import com.ideazlab.releaseninja.common.annotations.SCMMappedEntity
import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.model.DataObj
import com.ideazlab.releaseninja.common.plugins.scm.SCMPlugin

@SCMMappedEntity
data class SourceControlManager(
    val name: String,
    val scmPlugin: SCMPlugin
) : DataObj() {
    override fun update(update: DataDto) {
        TODO("Not yet implemented")
    }

    override fun getDTO(): DataDto {
        TODO("Not yet implemented")
    }
}