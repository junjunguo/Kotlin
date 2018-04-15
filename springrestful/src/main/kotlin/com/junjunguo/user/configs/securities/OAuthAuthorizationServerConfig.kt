package com.junjunguo.user.configs.securities

import com.junjunguo.user.system.securities.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
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

    @Value("\${security.jwt.resource-ids}")
    private lateinit var appResourceIds: String

    @Value("\${security.jwt.web-client-id}")
    private lateinit var appWebClientId: String

    @Value("\${security.jwt.web-client-secret}")
    private lateinit var appWebClientSecret: String

    @Value("\${security.jwt.web-access-token-validity-seconds}")
    private lateinit var appWebAccessTokenValiditySeconds: Integer

    @Value("\${security.jwt.web-refresh-token-validity-seconds}")
    private lateinit var appWebRefreshTokenValiditySeconds: Integer

    @Value("\${security.jwt.native-client-id}")
    private lateinit var appNativeClientId: String

    @Value("\${security.jwt.native-client-secret}")
    private lateinit var appNativeClientSecret: String

    @Value("\${security.jwt.native-access-token-validity-seconds}")
    private lateinit var appNativeAccessTokenValiditySeconds: Integer

    @Value("\${security.jwt.native-refresh-token-validity-seconds}")
    private lateinit var appNativeRefreshTokenValiditySeconds: Integer

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

//            .withClient(appWebClientId)
//            .secret(appWebClientSecret)
//            .resourceIds(appResourceIds)
//            .scopes("read", "write")
//            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//            .accessTokenValiditySeconds(appWebAccessTokenValiditySeconds.toInt())
//            .refreshTokenValiditySeconds(appWebRefreshTokenValiditySeconds.toInt())
//            .and()
//            .withClient(appNativeClientId)
//            .secret(appNativeClientSecret)
//            .resourceIds(appResourceIds)
//            .scopes("read", "write")
//            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//            .accessTokenValiditySeconds(appNativeAccessTokenValiditySeconds.toInt())
//            .refreshTokenValiditySeconds(appNativeRefreshTokenValiditySeconds.toInt())
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
//            .allowFormAuthenticationForClients() // (send client credentials in body) vs in header
            .passwordEncoder(passwordEncoder)
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
    }
}