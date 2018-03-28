package com.junjunguo.spring.controllers

import com.junjunguo.spring.models.api.User
import com.junjunguo.spring.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val service: UserService) {
    @GetMapping("/all")
    fun getAll() = this.service.getAll()

    @PostMapping("/add")
    fun add(@RequestBody person: User) = this.service.add(person)

    @PutMapping("/")
    fun update(@RequestBody person: User) = this.service.edit(person)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = this.service.delete(id)

    @GetMapping("/{id}")
    fun find(@PathVariable id: Long) = this.service.getById(id)
}