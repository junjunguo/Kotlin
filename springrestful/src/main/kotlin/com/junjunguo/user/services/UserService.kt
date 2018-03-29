package com.junjunguo.user.services

import com.junjunguo.user.models.api.UserModel

interface UserService {
    fun getById(id: Long): UserModel
    fun updateUser(id: Long, user: UserModel): UserModel
    fun getAll(): List<UserModel>
    fun add(userModel: UserModel): UserModel
}