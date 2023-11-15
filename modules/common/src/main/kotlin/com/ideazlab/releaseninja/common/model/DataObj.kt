package com.ideazlab.releaseninja.common.model

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import org.bson.types.ObjectId
import io.micronaut.data.annotation.Transient
import java.time.LocalDateTime

abstract class DataObj {
    @field:Id
    @GeneratedValue
    open var id: ObjectId? = null
    @DateCreated
    open var createdAt: LocalDateTime = LocalDateTime.now()
    @DateUpdated
    open var updatedAt: LocalDateTime = LocalDateTime.now()
    abstract fun update(update: DataDto)
    @Transient
    abstract fun getDTO(): DataDto
}