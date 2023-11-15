package com.ideazlab.releaseninja.common.annotations

import com.fasterxml.jackson.annotation.JsonInclude
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Introspected
@JsonInclude(JsonInclude.Include.NON_NULL)
annotation class NinjaResponseObj()
