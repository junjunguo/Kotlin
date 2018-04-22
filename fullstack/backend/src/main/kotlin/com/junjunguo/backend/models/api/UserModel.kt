package com.junjunguo.backend.models.api

import com.junjunguo.backend.models.entities.UserBaseEntity
import java.util.*

data class UserModel(
    var id: Long?,
    var email: String?,
    var name: String,
    var modified: Date?,
    var created: Date?,
    var uuid: UUID?
) {
    constructor(ue: UserBaseEntity) : this(ue.id, ue.email, ue.name, ue.modifiedDate, ue.createdDate, ue.uuid)
}