package com.junjunguo.backend.services.impl

import com.junjunguo.backend.models.api.UserModel
import com.junjunguo.backend.models.api.UserRegisterModel
import com.junjunguo.backend.models.entities.UserBaseEntity
import com.junjunguo.backend.repositories.UserRepository
import com.junjunguo.backend.services.UserService
import com.junjunguo.backend.settings.errorHanlder.exceptions.BadRequestException
import com.junjunguo.backend.settings.errorHanlder.exceptions.InternalServerException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val translateService: TranslateService

) : UserService {

    override fun getByName(username: String?): UserModel {
        if (username.isNullOrBlank()) throw InternalServerException("username null")

        return UserModel(userRepository.findByName(username!!).get())
    }

    @Throws(Exception::class)
    override fun getAll() = userRepository.findAll().map { UserModel(it) }

    @Throws(Exception::class)
    override fun updateUser(id: Long, user: UserModel): UserModel {

        val u = this.userRepository.findById(id).get().apply {
            email = user.email
            name = user.name
        }
        userRepository.save(u)
        return UserModel(u)
    }

    @Throws(Exception::class)
    override fun getById(id: Long): UserModel {
        return UserModel(this.userRepository.findById(id).get())
    }

    @Throws(Exception::class)
    override fun delete(id: Long) {
        userRepository.delete(userRepository.findById(id).get())
    }

    @Throws(Exception::class)
    override fun register(user: UserRegisterModel): UserModel {
        if (user.name.isBlank() || user.password.isBlank())
            throw BadRequestException(
                translateService.translate(
                    "bad_request.User_name_and_password_must_be_set"
                )
            )
        if (userRepository.findByName(user.name).isPresent)
            throw BadRequestException(
                translateService.translate(
                    "bad_request.User_name_already_taken"
                )
            )
        if (!user.email.isNullOrBlank() && userRepository.findByEmail(user.email!!).isPresent)
            throw BadRequestException(
                translateService.translate(
                    "bad_request.Email_already_taken"
                )
            )

        try {
            val u = UserBaseEntity(user.email, user.name, passwordEncoder.encode(user.password))
            val ue = userRepository.save(u)
            return UserModel(ue)
        } catch (e: Exception) {
            throw InternalServerException(e.message!!, e)
        }
    }

    override fun logout(username: String?) {
        userRepository.save(userRepository.findByName(username!!).get().apply { isCredentialsNonExpired = false })
    }
}