package com.junjunguo.backend.services

import com.junjunguo.backend.models.api.FriendModel

interface UserFriendService {

    @Throws(Exception::class)
    fun addFriend(userId: Long, friendId: Long): FriendModel

    @Throws(Exception::class)
    fun removeFriend(userId: Long, friendId: Long)

    @Throws(Exception::class)
    fun confirmFriendRequest(userId: Long, requesterId: Long): FriendModel

    @Throws(Exception::class)
    fun getAllFriends(userId: Long): List<FriendModel>?
}