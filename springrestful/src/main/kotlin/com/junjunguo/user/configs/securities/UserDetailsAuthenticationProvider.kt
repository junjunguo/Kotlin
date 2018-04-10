package com.junjunguo.user.configs.securities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
@Component
class UserDetailsAuthenticationProvider : AbstractUserDetailsAuthenticationProvider() {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun retrieveUser(username: String, authentication: UsernamePasswordAuthenticationToken): UserDetails {
        return userDetailsService.loadUserByUsername(username)
    }

    override fun additionalAuthenticationChecks(
        userDetails: UserDetails,
        token: UsernamePasswordAuthenticationToken
    ) {

        if (token.credentials == null || userDetails.password == null) {
            throw BadCredentialsException("- - -  - - - - - - - - - Credentials may not be null.")
        }

        if (!passwordEncoder.matches(token.credentials as String, userDetails.password)) {
            throw BadCredentialsException(" - - - - - - - -- - - - -  -Invalid credentials.")
        }
    }
}