package com.junjunguo.backend.settings.errorHanlder.exceptions

class RestException(
    override var message: String?,
    var args: Array<Any>?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean
) : RuntimeException(message, cause, enableSuppression, writableStackTrace) {
    constructor() : this(null, null, null, true, true)
    constructor(message: String?) : this(message, null, null, true, true)
    constructor(message: String?, args: Array<Any>?) : this(message, args, null, true, true)
}