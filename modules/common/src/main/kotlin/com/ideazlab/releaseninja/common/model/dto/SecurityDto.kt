package com.ideazlab.releaseninja.common.model.dto

import com.ideazlab.releaseninja.common.annotations.DTO
import com.ideazlab.releaseninja.common.model.DataDto
import com.ideazlab.releaseninja.common.utils.Role

@DTO
data class SecurityDto(
    var username:String,
    var password:String,
    var role: Role,
    var accountExpired: Boolean = false,
    var accountLocked: Boolean = false,
    var credentialsExpired: Boolean = false,
    var email:String,
    var enabled: Boolean = true,
    var name:String = "",
    var surname:String = ""
): DataDto()