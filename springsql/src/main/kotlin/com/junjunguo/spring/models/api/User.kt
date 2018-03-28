package com.junjunguo.spring.models.api

import java.util.*

data class User(
    var id: Long?,
    var email: String,
    var name: String,
    var modified: Date?,
    var created: Date?
)