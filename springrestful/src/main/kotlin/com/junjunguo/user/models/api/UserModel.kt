package com.junjunguo.user.models.api

import java.util.*

class UserModel(
    var id: Long?,
    var email: String,
    var name: String,
    var modified: Date?,
    var created: Date?
//    var uuid: UUID?
)