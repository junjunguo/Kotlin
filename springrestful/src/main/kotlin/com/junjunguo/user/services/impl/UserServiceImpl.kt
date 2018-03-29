package com.junjunguo.user.services.impl

import com.junjunguo.user.models.api.UserModel
import com.junjunguo.user.models.entities.UserEntity
import com.junjunguo.user.repositories.UserRepository
import com.junjunguo.user.services.UserService
import org.springframework.stereotype.Service


@Service
class UserServiceImpl(private val repo: UserRepository) : UserService {
    override fun getAll() = repo.findAll().map { UserModel(it.id, it.email, it.name, it.modified, it.created) }

    override fun add(userModel: UserModel): UserModel {
        val u = UserEntity(null, userModel.email, userModel.name, "1234567")
        repo.save(u)
        return UserModel(u.id, u.email, u.name, u.modified, u.created)
    }

    override fun updateUser(id: Long, user: UserModel): UserModel {
        var u = this.repo.findById(id).get()
        u.email = user.email
        u.name = user.name
        repo.save(u)
        return UserModel(u.id, u.email, u.name, u.modified, u.created)
    }

    override fun getById(id: Long): UserModel {
        val u = this.repo.findById(id).get()
        return UserModel(u.id, u.email, u.name, u.modified, u.created)
    }
}