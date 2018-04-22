package com.junjunguo.backend.controllers

import com.junjunguo.backend.models.api.UserRegisterModel
import com.junjunguo.backend.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal


@RestController
@RequestMapping("/auth/")
class OAuthController(val userService: UserService) {

    @GetMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    fun logout(principal: Principal) {
//        userService.logout(principal.name)
    }

    @PostMapping("register")
    fun register(@RequestBody user: UserRegisterModel) = userService.register(user)
}