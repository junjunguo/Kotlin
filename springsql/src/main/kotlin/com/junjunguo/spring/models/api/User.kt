package com.junjunguo.spring.models.api

import java.util.*

data class User(
    val id: Long? = 0,
    val email: String? = "",
    val name: String? = "",
    val timestamp: Date? = null
)