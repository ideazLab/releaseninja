package com.ideazlab.releaseninja.common.model

import java.time.LocalDateTime

abstract class DataDto {
    open var id: String? = ""
    open var createdAt: LocalDateTime? = null
    open var updatedAt:LocalDateTime? = null
}