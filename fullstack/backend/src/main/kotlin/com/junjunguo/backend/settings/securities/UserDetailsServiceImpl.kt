package com.junjunguo.backend.settings.securities

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
            return UserDetailsImpl(
                if (username.toLongOrNull() == null)
                    userRepository.findByName(username).get()
                else
                    userRepository.findById(username.toLong()).get()
            )

        } catch (e: Exception) {
            throw UsernameNotFoundException(username)
        }
    }
}