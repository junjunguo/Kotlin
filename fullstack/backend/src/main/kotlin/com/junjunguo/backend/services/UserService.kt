package com.junjunguo.backend.services

import com.junjunguo.backend.models.api.UserModel
import com.junjunguo.backend.models.api.UserRegisterModel
import java.util.*

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
    fun register(user: UserRegisterModel, locale: Locale): UserModel


    @Throws(Exception::class)
    fun logout(username: String?)

    fun getByName(username: String?): UserModel
}