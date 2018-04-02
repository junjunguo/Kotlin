package com.junjunguo.user.controllers

import com.junjunguo.user.models.api.UserModel
import com.junjunguo.user.models.api.UserRegisterModel
import com.junjunguo.user.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/")
class UserController(private val service: UserService) {

    @GetMapping("id/{id}")
    fun getUser(@PathVariable id: Long) = service.getById(id)

    @PutMapping("update")
    fun updateUser(@RequestBody user: UserModel): UserModel? {
        val id = user.id ?: -1
        if (id > -1)
            return service.updateUser(id, user)
        return null
    }

    @GetMapping("all")
    fun getAll() = this.service.getAll()

//    @PostMapping("add")
//    fun add(@RequestBody userModel: UserModel) = service.add(userModel)

    @DeleteMapping("id/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

    @PostMapping("register")
    fun register(@RequestBody user: UserRegisterModel) = service.register(user)
}