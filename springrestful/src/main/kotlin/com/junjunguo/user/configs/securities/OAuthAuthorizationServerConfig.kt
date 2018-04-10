package com.junjunguo.user.configs.securities

import com.junjunguo.user.system.securities.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
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
    @Qualifier("dataSource")
    private lateinit var dataSource: DataSource


    @Throws(Exception::class)
    override fun configure(client: ClientDetailsServiceConfigurer) {
        client
            .jdbc(dataSource)

            .passwordEncoder(passwordEncoder)

//            .withClient("client-id-A")
//            .authorizedGrantTypes("implicit")
//            .scopes("read")
//            .autoApprove(true)
//            .and()
//            .withClient("client-id-B")
//            .secret("client-id-B-secret")
//            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//            .scopes("read","write")
//            .resourceIds("client-id-B-resource-ids")
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
        oauthServer
            .allowFormAuthenticationForClients()
            .passwordEncoder(passwordEncoder)
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
    }
}