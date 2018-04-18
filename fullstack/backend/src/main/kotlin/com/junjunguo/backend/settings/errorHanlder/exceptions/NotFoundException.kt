package com.junjunguo.backend.settings.errorHanlder.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String, t: Throwable?) : RuntimeException(msg, t) {
    constructor(msg: String) : this(msg, null)
}