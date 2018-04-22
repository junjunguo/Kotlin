package com.junjunguo.backend.settings.securities

import com.junjunguo.backend.models.entities.UserBaseEntity
import com.junjunguo.backend.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Primary
@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository


    override fun loadUserByUsername(username: String): UserDetails {
        try {
            return UserDetailsImpl(findUserByName(username))
        } catch (e: Exception) {
            throw UsernameNotFoundException(username)
        }
    }

    fun findUserByName(name: String): UserBaseEntity {
        val id = name.toLongOrNull()
        if (id != null) {
            var user = userRepository.findById(id)
            if (user.isPresent) return user.get()
        }

        return userRepository.findByName(name).get()
    }
}