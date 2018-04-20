package com.junjunguo.backend.models.api

class RestMessage(
    var message: String?,
    var messages: List<String>?
) {
    constructor() : this(null, null)
    constructor(message: String?) : this(message, null)
    constructor(messages: List<String>?) : this(null, messages)
}