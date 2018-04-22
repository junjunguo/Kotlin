package com.junjunguo.backend.controllers

import com.junjunguo.backend.models.api.UserModel
import com.junjunguo.backend.services.UserService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/user/")
class UserController(private val service: UserService) {

    @GetMapping("id/{id}")
    fun getUser(@PathVariable id: Long, principal: Principal) = service.getById(id)

    @GetMapping("info")
    fun getRequesterInfo(principal : Principal) = service.getById(principal.name.toLong())

    @PutMapping("update")
    fun updateUser(@RequestBody user: UserModel, principal : Principal): UserModel? {
        val id = user.id ?: -1
        if (id > -1)
            return service.updateUser(id, user)
        return null
    }

    @GetMapping("all")
    fun getAll() = this.service.getAll()

    @DeleteMapping("id/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}