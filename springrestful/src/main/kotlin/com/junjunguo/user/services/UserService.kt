package com.junjunguo.user.services

import com.junjunguo.user.models.api.UserModel
import com.junjunguo.user.models.api.UserRegisterModel

interface UserService {
    fun getById(id: Long): UserModel
    fun updateUser(id: Long, user: UserModel): UserModel
    fun getAll(): List<UserModel>
    fun add(userModel: UserModel): UserModel
    fun delete(id: Long)
    fun register(user: UserRegisterModel): String
}