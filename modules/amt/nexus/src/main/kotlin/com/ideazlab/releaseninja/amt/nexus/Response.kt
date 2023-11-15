package com.ideazlab.releaseninja.amt.nexus

import com.ideazlab.releaseninja.common.api.response.BaseResponse

class CreateNexusResponse() : BaseResponse() {
    constructor(success: Boolean) : this() {
        this.success = success
    }

    constructor(message: String) : this() {
        this.message = message
    }

    constructor(success: Boolean, message: String) : this() {
        this.success = success
        this.message = message
    }
}