package com.junjunguo.user.services.impl

import com.junjunguo.user.models.api.UserModel
import com.junjunguo.user.models.api.UserRegisterModel
import com.junjunguo.user.models.entities.UserEntity
import com.junjunguo.user.repositories.UserRepository
import com.junjunguo.user.services.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserServiceImpl(private val repo: UserRepository, private val passwordEncoder: PasswordEncoder) : UserService {

    override fun getAll() = repo.findAll().map { UserModel(it) }

    override fun add(userModel: UserModel): UserModel {
        val u = UserEntity(null, userModel.email, userModel.name, "1234567")
        repo.save(u)
        return UserModel(u)
    }

    override fun updateUser(id: Long, user: UserModel): UserModel {
        var u = this.repo.findById(id).get().apply {
            email = user.email
            name = user.name
        }
        repo.save(u)
        return UserModel(u)
    }

    override fun getById(id: Long): UserModel {
        return UserModel(this.repo.findById(id).get())
    }

    override fun delete(id: Long) {
        repo.delete(repo.findById(id).get())
    }

    override fun register(user: UserRegisterModel): String {
        val u = UserEntity(user.name, passwordEncoder.encode(user.password))
        repo.save(u)
        return "token"
    }
}