package com.junjunguo.spring.daos

import com.junjunguo.spring.models.api.User
import com.junjunguo.spring.models.entities.UserEntity

interface UserDao {
    fun getAll(): MutableList<UserEntity>
    fun add(p: User): UserEntity
    fun getById(id: Long): UserEntity
    fun delete(id: Long)
    fun edit(user: User): UserEntity
}