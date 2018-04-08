package com.junjunguo.user.system.errorHanlder

import java.time.LocalDateTime

data class ApiErrorModel(
    var timestamp: LocalDateTime? = LocalDateTime.now(),
    var status: Int?,
    var error: String?,
    var message: String?,
    var errors: List<String>?
) {
    constructor(status: Int, error: String, message: String) : this(
        LocalDateTime.now(), status, error, message, null
    )

    constructor(status: Int, error: String, message: String, errors: List<String>?) : this(
        LocalDateTime.now(), status, error, message, errors
    )

}