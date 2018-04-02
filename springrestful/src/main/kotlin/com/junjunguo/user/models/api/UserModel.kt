package com.junjunguo.user.models.api

import com.junjunguo.user.models.entities.UserEntity
import java.util.*

data class UserModel(
    var id: Long?,
    var email: String?,
    var name: String,
    var modified: Date?,
    var created: Date?,
    var uuid: UUID?
) {
    constructor(ue: UserEntity) : this(ue.id, ue.email, ue.name, ue.modified, ue.created, ue.uuid)
}