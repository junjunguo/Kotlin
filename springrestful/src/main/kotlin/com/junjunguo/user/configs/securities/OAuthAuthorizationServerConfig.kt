package com.junjunguo.user.configs.securities

import com.junjunguo.user.system.securities.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class OAuthAuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {


    @Value("\${security.jwt.client-id}")
    private lateinit var clientId: String

    @Value("\${security.jwt.client-secret}")
    private lateinit var clientSecret: String

    @Value("\${security.jwt.grant-type}")
    private lateinit var grantType: String

    @Value("\${security.jwt.scope-read}")
    private lateinit var scopeRead: String

    @Value("\${security.jwt.scope-write}")
    private val scopeWrite = "write"

    @Value("\${security.jwt.resource-ids}")
    private lateinit var resourceIds: String

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Autowired
    private lateinit var accessTokenConverter: JwtAccessTokenConverter


    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userDetailsService: UserDetailsServiceImpl

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var dataSource: DataSource


    @Throws(Exception::class)
    override fun configure(configurer: ClientDetailsServiceConfigurer) {
        configurer
            .jdbc(dataSource)
            .passwordEncoder(passwordEncoder)

//            .inMemory()  //
//            .withClient(clientId)
//            .secret(clientSecret)
//            .authorizedGrantTypes(grantType)
//            .authorities("USER")
//            .scopes(scopeRead, scopeWrite)
//            .resourceIds(resourceIds)
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
            .tokenStore(tokenStore)
            .authenticationManager(authenticationManager)
            .accessTokenConverter(accessTokenConverter)
            .userDetailsService(userDetailsService)
    }

    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer.allowFormAuthenticationForClients()
            .passwordEncoder(passwordEncoder)
    }
}