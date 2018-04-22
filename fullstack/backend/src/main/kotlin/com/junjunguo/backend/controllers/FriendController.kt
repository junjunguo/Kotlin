package com.junjunguo.backend.controllers

import com.junjunguo.backend.services.UserFriendService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/friend/")
class FriendController(private val userFriendService: UserFriendService) {


    @PostMapping("id/{id}")
    fun addFriend(@PathVariable id: Long, principal: Principal) =
        userFriendService.addFriend(principal.name.toLong(), id)

    @DeleteMapping("id/{id}")
    fun deleteFriend(@PathVariable id: Long, principal: Principal) =
        userFriendService.removeFriend(principal.name.toLong(), id)

    @PutMapping("id/{id}")
    fun confirmFriend(@PathVariable id: Long, principal: Principal) =
        userFriendService.confirmFriendRequest(principal.name.toLong(), id)

    @GetMapping("all")
    fun getFriends(principal: Principal) = userFriendService.getAllFriends(principal.name.toLong())
}