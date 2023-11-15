package com.ideazlab.releaseninja.amt.nexus.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.ideazlab.releaseninja.common.annotations.EmbededData
import com.ideazlab.releaseninja.common.plugins.amt.AMTSecurity

@EmbededData
data class NexusSecurity(
    override val username: String,
    override val password: String,
    override val url: String,
    override val publicRepository: String = "maven-public",
    override val repository: String = "maven-releases",
    override val snapshotRepository: String = "maven-snapshots"
) : AMTSecurity()