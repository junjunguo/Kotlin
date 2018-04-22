package com.junjunguo.backend.models.api

import com.junjunguo.backend.models.entities.UserBaseEntity
import com.junjunguo.backend.models.enums.FriendStatusApi


data class FriendModel(
    var id: Long?,
    var email: String?,
    var name: String?,
    var status: FriendStatusApi
) {
    constructor(ue: UserBaseEntity, status: FriendStatusApi) : this(ue.id, ue.email, ue.name, status)
}