package com.ideazlab.releaseninja.common.plugins.amt

import com.ideazlab.releaseninja.common.annotations.EmbededData
import jakarta.validation.constraints.NotBlank

@EmbededData
abstract class AMTSecurity {
    @NotBlank
    open val url: String = ""

    @NotBlank
    open val username: String = ""

    @NotBlank
    open val password: String = ""
    @NotBlank
    open val publicRepository: String = ""

    @NotBlank
    open val repository: String = ""

    @NotBlank
    open val snapshotRepository: String = ""
}