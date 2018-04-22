package com.junjunguo.backend.services.impl

import com.junjunguo.backend.models.api.FriendModel
import com.junjunguo.backend.models.entities.UserFriendsEntity
import com.junjunguo.backend.models.enums.FriendStatus
import com.junjunguo.backend.models.enums.FriendStatusApi

class FriendUtil {
    companion object {
        fun getFriendModel(userFriendsEntity: UserFriendsEntity, userId: Long): FriendModel {
            return if (userFriendsEntity.firstUser.id == userId)
                FriendModel(userFriendsEntity.secondUser, getFriendStatusApi(userFriendsEntity.status, true))
            else FriendModel(userFriendsEntity.firstUser, getFriendStatusApi(userFriendsEntity.status, false))
        }

        fun getFriendStatusApi(friendStatus: FriendStatus, isFirstUser: Boolean): FriendStatusApi {
            return if (friendStatus == FriendStatus.FRIEND) FriendStatusApi.FRIEND
            else if ((friendStatus == FriendStatus.PENDING_FIRST_SECOND && isFirstUser) ||
                (friendStatus == FriendStatus.PENDING_SECOND_FIRST && !isFirstUser)
            ) FriendStatusApi.FRIEND_REQUEST_SENT
            else FriendStatusApi.PENDING
        }
    }
}