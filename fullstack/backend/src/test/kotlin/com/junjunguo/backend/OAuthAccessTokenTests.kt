package com.junjunguo.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.junjunguo.backend.models.api.UserModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.oauth2.common.util.JacksonJsonParser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.web.FilterChainProxy
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.context.WebApplicationContext
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest()
@WebAppConfiguration
@ActiveProfiles("test")
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
    private val oauth_token_url = "/auth/token"
    private val accessTokenTest = "AccessTokenTest"

    @Before
    fun setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .addFilter<DefaultMockMvcBuilder>(springSecurityFilterChain).build()
    }

    @Test
    @Throws(Exception::class)
    fun testUnauthorizedWithoutToken() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/user/id/")
                .param("id", "1")
        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAccessTokenAndGetFirstUser() {
        val accessToken: String = getAccessToken(accessTokenTest, accessTokenTest)
        testGetUserById("1", accessToken)
    }

    @Throws(Exception::class)
    private fun getAccessToken(username: String, password: String): String {
        return getOAuthTokensTypePassword(username, password)[access_token].toString()
    }

    @Throws(Exception::class)
    fun getOAuthTokensTypePassword(username: String, password: String): Map<String, *> {
        val authorization = "Basic " +
                String(
                    Base64.getEncoder()
                        .encode(
                            "$appWebClientId:$appWebClientSecret"
                                .toByteArray(charset("UTF-8"))
                        )
                )

        val content = mockMvc.perform(
            MockMvcRequestBuilders.post(oauth_token_url)
                .header("Authorization", authorization)
                .contentType(
                    MediaType.APPLICATION_FORM_URLENCODED
                )
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password")
//                .param("scope", "read write")
//                .param("client_id", appWebClientId)
//                .param("client_secret", appWebClientSecret)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        return JacksonJsonParser().parseMap(content.andReturn().response.contentAsString)
    }

//    @Test
//    @Throws(Exception::class)
//    fun testRevokeAccessToken() {
//        val accessToken: String = getAccessToken(accessTokenTest, accessTokenTest)
//        testGetUserById("1", accessToken)
//
//        mockMvc.perform(
//            MockMvcRequestBuilders.get("/oauth/revoke-token")
//                .header("Authorization", "Bearer $accessToken")
//                .accept(CONTENT_TYPE)
//        )
//            .andExpect(MockMvcResultMatchers.status().isOk)
//    }

    @Test
    @Throws(Exception::class)
    fun testGetAccessTokenWithRefreshToken() {
        val tokens = getOAuthTokensTypePassword(accessTokenTest, accessTokenTest)
        val token = tokens[access_token].toString()
        val refreshToken = tokens[refresh_token].toString()

        testGetUserById("1", token)

        val newTokens = getAccessTokenFromRefreshToken(refreshToken)

        testGetUserById("1", newTokens[access_token].toString())

        testGetUserById("1", token)

    }

    private fun testGetUserById(userId: String, accessToken: String) {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/user/id/$userId")
                .header("Authorization", "Bearer $accessToken")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn()
        val model = ObjectMapper().findAndRegisterModules().readValue<UserModel>(result.response.contentAsString)
        assert(model.id.toString() == "1")
        assert(model.name.isNotEmpty())
        assert(model.uuid.toString().isNotEmpty())
    }


    @Throws(Exception::class)
    private fun getAccessTokenFromRefreshToken(refreshToken: String): Map<String, *> {
        val params = LinkedMultiValueMap<String, String>()
        params.add("grant_type", "refresh_token")
        params.add("client_id", appWebClientId)
        params.add("refresh_token", refreshToken)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post(oauth_token_url)
                .params(params)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(appWebClientId, appWebClientSecret))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        return JacksonJsonParser().parseMap(result.andReturn().response.contentAsString)
    }

}