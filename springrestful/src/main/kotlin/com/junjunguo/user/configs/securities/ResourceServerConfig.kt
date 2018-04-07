package com.junjunguo.user.configs.securities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore

/**
 * <h1>Resource Server Configuration</h1>
 * A Resource Server (can be the same as the Authorization Server or a separate application) serves resources that
 * are protected by the OAuth2 token.
 * Spring OAuth provides a Spring Security authentication filter that implements this protection.
 * You can switch it on with
 *      @EnableResourceServer on an
 *      @Configuration class, and configure it (as necessary) using a
 *      ResourceServerConfigurer.
 * The following features can be configured:
 * - tokenServices: the bean that defines the token services (instance of ResourceServerTokenServices).
 * - resourceId: the id for the resource
 *      (optional, but recommended and will be validated by the auth server if present).
 * - other extension points for the resourecs server
 *      (e.g. tokenExtractor for extracting the tokens from incoming requests)
 * - request matchers for protected resources (defaults to all)
 * - access rules for protected resources (defaults to plain "authenticated")
 * - other customizations for the protected resources permitted by the HttpSecurity configurer in Spring Security
 *
 * The @EnableResourceServer annotation adds a
 *      filter of type OAuth2AuthenticationProcessingFilter automatically to the Spring Security filter chain.
 */
@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Autowired
    private lateinit var tokenServices: ResourceServerTokenServices

    @Value("\${security.jwt.resource-ids}")
    private lateinit var resourceIds: String

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.tokenStore(tokenStore)
//        resources.resourceId(resourceIds).tokenServices(tokenServices)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .requestMatchers()
            .and()
            .authorizeRequests()
            .antMatchers("/user/register", "/api-docs/**").permitAll()
            .antMatchers("/springjwt/**").authenticated()
    }

}