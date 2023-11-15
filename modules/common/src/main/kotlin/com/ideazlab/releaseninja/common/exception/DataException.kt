package com.ideazlab.releaseninja.common.exception

class DataException:Exception {
    constructor ()
    constructor (message: String): super(message)
    constructor (message: String, cause: Throwable): super(message, cause)
    constructor (cause: Throwable): super(cause)
}