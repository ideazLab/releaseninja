package com.ideazlab.releaseninja.common.api.request.project

import com.ideazlab.releaseninja.common.annotations.NinjaRequestObj
import com.ideazlab.releaseninja.common.api.request.BaseRequest
import com.ideazlab.releaseninja.common.model.dto.Artifact
import com.ideazlab.releaseninja.common.utils.Status

@NinjaRequestObj()
data class CreateProjectRelease(
    override val id: String? = "",
    val version: String,
    val date: String,
    var artifacts: MutableList<Artifact>
) : BaseRequest()


@NinjaRequestObj()
data class UpdateProjectGeneralInfo(
    override val id: String? = "",
    val name: String,
    val description: String,
    val status: Status? = Status.LIVE,
    val amt: String? = ""
) : BaseRequest()

@NinjaRequestObj()
data class UpdateCurrentRelease(
    override val id: String? = "",
    val currentRelease: String
) : BaseRequest()

@NinjaRequestObj()
data class UpdateArtifact(
    override val id: String? = "",
    val artifactId: String,
    val group: String,
    val name: String,
    val version: String,
    val type: String,
    val visibility: String,
) : BaseRequest()

@NinjaRequestObj()
data class DeleteArtifact(
    override val id: String? = "",
    val artifactId: String,
    val name: String,
) : BaseRequest()

@NinjaRequestObj()
data class CreateArtifact(
    override val id: String? = "",
    val group: String,
    val name: String,
    val version: String,
    val type: String,
    val visibility: String,
) : BaseRequest()