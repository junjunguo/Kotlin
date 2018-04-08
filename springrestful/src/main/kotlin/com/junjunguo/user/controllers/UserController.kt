package com.junjunguo.user.controllers

import com.junjunguo.user.models.api.UserModel
import com.junjunguo.user.models.api.UserRegisterModel
import com.junjunguo.user.services.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/")
class UserController(private val service: UserService) {

    @GetMapping("id/{id}")
//    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    fun getUser(@PathVariable id: Long) = service.getById(id)

    @PutMapping("update")
    fun updateUser(@RequestBody user: UserModel, @AuthenticationPrincipal activeUser: UserDetails): UserModel? {
        println("- - - - -- - - - - - -  - - - - --  - "+activeUser.toString())
        val id = user.id ?: -1
        if (id > -1)
            return service.updateUser(id, user)
        return null
    }

    @GetMapping("all")
    fun getAll() = this.service.getAll()

    @DeleteMapping("id/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

    @PostMapping("register")
    fun register(@RequestBody user: UserRegisterModel) = service.register(user)
}