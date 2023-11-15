package com.ideazlab.releaseninja.common.api.response

import com.ideazlab.releaseninja.common.annotations.NinjaResponseObj
import jakarta.validation.constraints.NotEmpty

@NinjaResponseObj
open class BaseResponse {
    constructor ()
    constructor (success: Boolean, message: String) {
        this.message = message
        this.success = success
    }

    constructor (success: Boolean, message: String, id: String) : this(success, message) {
        this.id = id
    }

    open var success: Boolean = true

    @NotEmpty
    open var message: String = "Success"
    open var id: String? = null
}