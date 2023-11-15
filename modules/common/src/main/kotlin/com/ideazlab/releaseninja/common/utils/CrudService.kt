package com.ideazlab.releaseninja.common.utils

import java.util.*
import kotlin.jvm.Throws

interface CrudService<T> {
    @Throws(Exception::class)
    fun create(entity: T): T?
    fun read(key: String): Optional<T>
    fun update(entity: T)
    fun delete(entity: T)
    fun list(): List<T>
}