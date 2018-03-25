package com.junjunguo.spring.controllers

import com.junjunguo.spring.services.UserService

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (private val service: UserService) {
    @GetMapping("/all")
    fun getAll()= this.service.getAll()
}