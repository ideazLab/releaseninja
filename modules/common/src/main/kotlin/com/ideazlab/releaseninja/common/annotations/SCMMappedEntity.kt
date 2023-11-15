package com.ideazlab.releaseninja.common.annotations

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@MappedEntity(value = "scm")
@Serdeable
@Introspected
annotation class SCMMappedEntity