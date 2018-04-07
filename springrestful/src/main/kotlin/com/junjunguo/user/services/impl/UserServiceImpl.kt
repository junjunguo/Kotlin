package com.junjunguo.user.services.impl

import com.junjunguo.user.models.api.UserModel
import com.junjunguo.user.models.api.UserRegisterModel
import com.junjunguo.user.models.entities.UserEntity
import com.junjunguo.user.repositories.UserRepository
import com.junjunguo.user.services.UserService
import com.junjunguo.user.system.errorHanlder.exceptions.BadRequestException
import com.junjunguo.user.system.errorHanlder.exceptions.InternalServerException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserServiceImpl(private val repo: UserRepository, private val passwordEncoder: PasswordEncoder) : UserService {

    @Throws(Exception::class)
    override fun getAll() = repo.findAll().map { UserModel(it) }

    @Throws(Exception::class)
    override fun updateUser(id: Long, user: UserModel): UserModel {
        var u = this.repo.findById(id).get().apply {
            email = user.email
            name = user.name
        }
        repo.save(u)
        return UserModel(u)
    }

    @Throws(Exception::class)
    override fun getById(id: Long): UserModel {
        return UserModel(this.repo.findById(id).get())
    }

    @Throws(Exception::class)
    override fun delete(id: Long) {
        repo.delete(repo.findById(id).get())
    }

    @Throws(Exception::class)
    override fun register(user: UserRegisterModel): UserModel {
        if (user.name.isBlank() || user.password.isBlank())
            throw BadRequestException("User name & password must be set!")
        if (repo.findByName(user.name).isPresent)
            throw BadRequestException("User name already taken!")
        if (user.email != null && repo.findByEmail(user.email).isPresent)
            throw BadRequestException("Email already taken!")

        try {

            val u = UserEntity(user.name, passwordEncoder.encode(user.password))
            val ue = repo.save(u)
            return UserModel(ue)
        } catch (e: Exception) {
            throw InternalServerException(e.message!!, e)
        }
    }
}