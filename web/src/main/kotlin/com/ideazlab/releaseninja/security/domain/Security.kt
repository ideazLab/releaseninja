package com.ideazlab.releaseninja.security.domain

import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.model.DataObj
import com.ideazlab.releaseninja.common.model.dto.SecurityDto
import com.ideazlab.releaseninja.common.utils.Role
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@MappedEntity(value = "security")
@Serdeable
@Introspected
data class Security(
    var username:String,
    var password:String,
    var role: Role = Role.USER,
    var accountExpired: Boolean = false,
    var accountLocked: Boolean = false,
    var credentialsExpired: Boolean = false,
    var email:String ="",
    var enabled: Boolean = true,
    var name:String = "",
    var surname:String = ""
): DataObj() {
    override fun update(update: DataDto) {
        val dto = update as SecurityDto
        role = dto.role
        accountExpired = dto.accountExpired
        accountLocked = dto.accountLocked
        credentialsExpired = dto.credentialsExpired
        email = dto.email
        enabled = dto.enabled
    }

    override fun getDTO(): SecurityDto {
        val dto = SecurityDto(
            name = name,
            surname = surname,
            username = username,
            password = password,
            role = role,
            accountExpired = accountExpired,
            accountLocked = accountLocked,
            credentialsExpired = credentialsExpired,
            email = email,
            enabled = enabled
        )
        dto.id = id!!.toHexString()
        return dto
    }
}
