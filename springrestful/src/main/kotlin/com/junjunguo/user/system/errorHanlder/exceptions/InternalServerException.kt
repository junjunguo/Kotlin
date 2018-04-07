package com.junjunguo.user.system.errorHanlder.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Internal server error will be logged.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerException(msg: String, t: Throwable?) : RuntimeException(msg, t) {
    constructor(msg: String) : this(msg, null)
}