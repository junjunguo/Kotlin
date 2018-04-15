package com.junjunguo.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.junjunguo.user.models.api.UserModel
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.util.LinkedMultiValueMap
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Test
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.security.web.FilterChainProxy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.json.JacksonJsonParser
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@WebAppConfiguration
@SpringBootTest()
@ActiveProfiles("mvc")
class OAuthAccessTokenTests {

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
    private lateinit var wac: WebApplicationContext

    @Autowired
    private lateinit var springSecurityFilterChain: FilterChainProxy

    private lateinit var mockMvc: MockMvc

    private val access_token = "access_token"
    private val refresh_token = "refresh_token"

    private val CONTENT_TYPE = "application/json;charset=UTF-8"

    @Before
    fun setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .addFilter<DefaultMockMvcBuilder>(springSecurityFilterChain).build()
    }


    @Test
    @Throws(Exception::class)
    fun testUnauthorizedWithoutToken() {
        mockMvc.perform(
            get("/user/id/")
                .param("id", "1")
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAccessTokenAndGetFirstUser() {
        val accessToken: String = getAccessToken("test", "test")
        testGetUserById("1", accessToken)
    }

    @Test
    @Throws(Exception::class)
    fun testRevokeAccessToken() {
        val accessToken: String = getAccessToken("test", "test")
        testGetUserById("1", accessToken)

        mockMvc.perform(
            get("/oauth/revoke-token")
                .header("Authorization", "Bearer $accessToken")
                .accept(CONTENT_TYPE)
        )
            .andExpect(status().isOk)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAccessTokenWithRefreshToken() {
        val tokens = getOAuthTokensTypePassword("test", "test")
        val token = tokens[access_token].toString()
        val refreshToken = tokens[refresh_token].toString()

        testGetUserById("1", token)

        val newTokens = getAccessTokenFromRefreshToken(refreshToken)

        testGetUserById("1", newTokens[access_token].toString())

        testGetUserById("1", token)

    }

    private fun testGetUserById(userId: String, accessToken: String) {
        val result = mockMvc.perform(
            get("/user/id/$userId")
                .header("Authorization", "Bearer $accessToken")
                .accept(CONTENT_TYPE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(CONTENT_TYPE))
            .andReturn()
        val model = ObjectMapper().findAndRegisterModules().readValue<UserModel>(result.response.contentAsString)
        assert(model.id.toString() == "1")
        assert(model.name.isNotEmpty())
        assert(model.uuid.toString().isNotEmpty())
    }


    @Throws(Exception::class)
    private fun getAccessToken(username: String, password: String): String {
        return getOAuthTokensTypePassword(username, password)[access_token].toString()
    }

    @Throws(Exception::class)
    private fun getOAuthTokensTypePassword(username: String, password: String): Map<String, *> {
        val params = LinkedMultiValueMap<String, String>()
        params.add("grant_type", "password")
        params.add("client_id", appWebClientId)
        params.add("username", username)
        params.add("password", password)

        val result = mockMvc.perform(
            post("/oauth/token")
                .params(params)
                .with(httpBasic(appWebClientId, appWebClientSecret))
                .accept(CONTENT_TYPE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(CONTENT_TYPE))

        return JacksonJsonParser().parseMap(result.andReturn().response.contentAsString)
    }

    @Throws(Exception::class)
    private fun getAccessTokenFromRefreshToken(refreshToken: String): Map<String, *> {
        val params = LinkedMultiValueMap<String, String>()
        params.add("grant_type", "refresh_token")
        params.add("client_id", appWebClientId)
        params.add("refresh_token", refreshToken)

        val result = mockMvc.perform(
            post("/oauth/token")
                .params(params)
                .with(httpBasic(appWebClientId, appWebClientSecret))
                .accept(CONTENT_TYPE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(CONTENT_TYPE))
        return JacksonJsonParser().parseMap(result.andReturn().response.contentAsString)
    }

}