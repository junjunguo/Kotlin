package com.junjunguo.backend.controllers

import com.junjunguo.backend.models.api.FriendRequest
import com.junjunguo.backend.services.UserFriendService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/friend/")
class FriendController(private val userFriendService: UserFriendService) {

    @PostMapping("add")
    fun addFriend(@RequestBody friend: FriendRequest, principal: Principal) =
        userFriendService.addFriend(principal.name.toLong(), friend.friendId)

    @PutMapping("remove")
    fun deleteFriend(@RequestBody friend: FriendRequest, principal: Principal) =
        userFriendService.removeFriend(principal.name.toLong(), friend.friendId)

    @PutMapping("confirm")
    fun confirmFriend(@RequestBody friend: FriendRequest, principal: Principal) =
        userFriendService.confirmFriendRequest(principal.name.toLong(), friend.friendId)

    @GetMapping("all")
    fun getFriends(principal: Principal) = userFriendService.getAllFriends(principal.name.toLong())
}