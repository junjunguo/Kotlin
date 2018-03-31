package com.junjunguo.user.configs

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer


@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    private val RESOURCE_ID = "resource-server-rest-api"
    private val SECURED_READ_SCOPE = "#oauth2.hasScope('read')"
    private val SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')"
    private val SECURED_PATTERN = "/secured/**"

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(RESOURCE_ID)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.requestMatchers()
            .antMatchers(SECURED_PATTERN).and().authorizeRequests()
            .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
            .anyRequest().access(SECURED_READ_SCOPE)
    }

}