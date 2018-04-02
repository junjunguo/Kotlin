package com.junjunguo.user.services

import com.junjunguo.user.models.api.UserModel
import com.junjunguo.user.models.api.UserRegisterModel

interface UserService {
    @Throws(Exception::class)
    fun getById(id: Long): UserModel

    @Throws(Exception::class)
    fun updateUser(id: Long, user: UserModel): UserModel

    @Throws(Exception::class)
    fun getAll(): List<UserModel>

    @Throws(Exception::class)
    fun delete(id: Long)

    @Throws(Exception::class)
    fun register(user: UserRegisterModel): UserModel
}