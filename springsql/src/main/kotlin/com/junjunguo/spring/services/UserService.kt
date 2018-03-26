package com.junjunguo.spring.services

import com.junjunguo.spring.models.api.User

interface UserService {
    fun getAll(): List<User>
    fun add(person: User): User
}