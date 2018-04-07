package com.junjunguo.user.system.securities

import com.junjunguo.user.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(private val repo: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        try {
            return UserDetailsImpl(
                if (username.toLongOrNull() == null)
                    repo.findByEmail(username).get()
                else
                    repo.findById(username.toLong()).get()
            )

        } catch (e: Exception) {
            throw UsernameNotFoundException(username)
        }
    }
}