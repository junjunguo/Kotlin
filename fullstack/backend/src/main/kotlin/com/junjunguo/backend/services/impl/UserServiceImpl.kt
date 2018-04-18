package com.junjunguo.backend.services.impl

import com.junjunguo.backend.models.api.UserModel
import com.junjunguo.backend.models.api.UserRegisterModel
import com.junjunguo.backend.models.entities.UserEntity
import com.junjunguo.backend.repositories.UserRepository
import com.junjunguo.backend.services.UserService
import com.junjunguo.backend.settings.errorHanlder.exceptions.BadRequestException
import com.junjunguo.backend.settings.errorHanlder.exceptions.InternalServerException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserServiceImpl(private val repo: UserRepository, private val passwordEncoder: PasswordEncoder) : UserService {
    override fun getByName(username: String?): UserModel {
        if (username.isNullOrBlank()) throw InternalServerException("username null")

        return UserModel(repo.findByName(username!!).get())
    }

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

    override fun logout(username: String?) {
        repo.save(repo.findByName(username!!).get().apply { isCredentialsNonExpired = false })
    }
}