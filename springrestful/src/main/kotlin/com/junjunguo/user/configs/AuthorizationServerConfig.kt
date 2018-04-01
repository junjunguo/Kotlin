package com.junjunguo.user.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.util.*

/**
 * <h1>Authorization Server Configuration</h1>
 * have to consider the grant type that the client is to use to obtain an access token from the end-user
 * - (e.g. authorization code, user credentials, refresh token).
 * The configuration of the server is used to provide implementations of the client details service and
 * token services and to enable or disable certain aspects of the mechanism globally.
 * Note, however, that each client can be configured specifically with permissions to be able to
 * use certain authorization mechanisms and access grants.
 * - I.e. just because your provider is configured to support the "client credentials" grant type,
 *      doesn't mean that a specific client is authorized to use that grant type.
 */
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {


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

    /**
     * ClientDetailsServiceConfigurer: a configurer that defines the client details service.
     * Client details can be initialized, or you can just refer to an existing store.
     */
    @Throws(Exception::class)
    override fun configure(configurer: ClientDetailsServiceConfigurer) {
        configurer
            .inMemory()
            .withClient(clientId)
            .secret(clientSecret)
            .authorizedGrantTypes(grantType)
            .scopes(scopeRead, scopeWrite)
            .resourceIds(resourceIds)
    }

    /**
     * AuthorizationServerEndpointsConfigurer:
     * defines the authorization and token endpoints and the token services.
     * *The grant types* supported by the AuthorizationEndpoint can be configured via
     * the AuthorizationServerEndpointsConfigurer.
     * -  authenticationManager: password grants are switched on by injecting an AuthenticationManager
     * -  userDetailsService: if you inject a UserDetailsService or if one is configured globally anyway
     *      (e.g. in a GlobalAuthenticationManagerConfigurer) then a refresh token grant will
     *      contain a check on the user details, to ensure that the account is still active
     * -  authorizationCodeServices: defines the authorization code services
     *      (instance of AuthorizationCodeServices) for the auth code grant.
     * -    implicitGrantService: manages state during the imlpicit grant.
     * -  tokenGranter: the TokenGranter
     *      (taking full control of the granting and ignoring the other properties above)
     *
     * The AuthorizationServerEndpointsConfigurer has a pathMapping() method. It takes two arguments:
     * 1. The default (framework implementation) URL path for the endpoint
     * 2. The custom path required (starting with a "/")
     * The URL paths provided by the framework are:
     *      /oauth/authorize (the authorization endpoint),
     *      /oauth/token (the token endpoint),
     *      /oauth/confirm_access (user posts approval for grants here),
     *      /oauth/error (used to render errors in the authorization server),
     *      /oauth/check_token (used by Resource Servers to decode access tokens), and
     *      /oauth/token_key (exposes public key for token verification if using JWT tokens).
     */
    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val enhancerChain = TokenEnhancerChain()
        enhancerChain.setTokenEnhancers(Arrays.asList<TokenEnhancer>(accessTokenConverter))
        endpoints.tokenStore(tokenStore)
            .accessTokenConverter(accessTokenConverter)
            .tokenEnhancer(enhancerChain)
            .authenticationManager(authenticationManager)
    }


}