package com.ideazlab.releaseninja.common.annotations

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@MappedEntity(value = "amt")
@Serdeable
@Introspected
annotation class AMTData()
