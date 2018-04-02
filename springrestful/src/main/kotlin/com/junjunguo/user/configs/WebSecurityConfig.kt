package com.junjunguo.user.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${security.signing-key}")
    private lateinit var signingKey: String

//    @Value("\${security.encoding-strength}")
//    private lateinit var encodingStrength: Int

    @Value("\${security.security-realm}")
    private lateinit var securityRealm: String

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    /**
     * the Authorization endpoint
     *      /oauth/authorize (or its mapped alternative)
     * should be protected using Spring Security so that it is only accessible to authenticated users.
     * For instance using a standard Spring Security WebSecurityConfigurer
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .httpBasic().realmName(securityRealm).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //allow Options: headers ...
//            .antMatchers("/login").permitAll()
//            .antMatchers("/user/register").permitAll()
            .anyRequest().authenticated()
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(signingKey)
        return converter
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    @Primary //Making this primary to avoid any accidental duplication with another token service instance of the same name
    fun tokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore())
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}