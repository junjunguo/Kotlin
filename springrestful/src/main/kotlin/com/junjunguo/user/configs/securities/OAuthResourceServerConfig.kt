package com.junjunguo.user.configs.securities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
@EnableResourceServer
class OAuthResourceServerConfig : ResourceServerConfigurerAdapter() {

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Value("\${security.jwt.resource-ids}")
    private lateinit var resourceIds: String


    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources
            .resourceId(resourceIds)
            .tokenStore(tokenStore)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //allow Options: headers ...
            .antMatchers("/user/register").permitAll()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
    }

}