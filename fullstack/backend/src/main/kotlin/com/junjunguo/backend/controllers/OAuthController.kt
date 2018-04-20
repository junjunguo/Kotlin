package com.junjunguo.backend.controllers

import com.junjunguo.backend.models.api.UserRegisterModel
import com.junjunguo.backend.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*


@Controller
@RequestMapping("/auth/")
class OAuthController(val userService: UserService) {

    @GetMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    fun logout(principal : Principal) {
//        userService.logout(principal.name)
    }

    @PostMapping("register")
    fun register(@RequestBody user: UserRegisterModel, locale: Locale) = userService.register(user, locale)
}