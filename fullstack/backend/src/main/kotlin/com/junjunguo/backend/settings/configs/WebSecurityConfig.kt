package com.junjunguo.backend.settings.configs.securities

import com.junjunguo.backend.settings.securities.AppBasicAuthenticationEntryPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${security.signing-key}")
    private lateinit var signingKey: String

    @Value("\${security.security-realm}")
    private lateinit var securityRealm: String

    @Autowired
    @Qualifier("dataSource")
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var appBasicAuthenticationEntryPoint: AppBasicAuthenticationEntryPoint

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .authenticationProvider(authenticationProvider())
            .jdbcAuthentication()
            .dataSource(dataSource)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .httpBasic()
            .realmName(securityRealm)
            .authenticationEntryPoint(appBasicAuthenticationEntryPoint).and()
            .csrf().disable()
            .headers().cacheControl()
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(signingKey)
        return converter
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }
}