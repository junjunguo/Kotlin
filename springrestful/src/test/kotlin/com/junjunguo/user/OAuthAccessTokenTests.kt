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

    @Autowired
    private lateinit var wac: WebApplicationContext

    @Autowired
    private lateinit var springSecurityFilterChain: FilterChainProxy

    private var mockMvc: MockMvc? = null

    private val CLIENT_ID = "client-id-B"
    private val CLIENT_SECRET = "client-id-B-secret"

    private val CONTENT_TYPE = "application/json;charset=UTF-8"

    @Before
    fun setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac!!)
            .addFilter<DefaultMockMvcBuilder>(springSecurityFilterChain).build()
    }


    @Test
    @Throws(Exception::class)
    fun testUnauthorizedWithoutToken() {
        mockMvc!!.perform(
            get("/user/id/")
                .param("id", "1")
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAccessTokenAndGetFirstUser() {
        val accessToken: String = obtainAccessToken("test", "test")
        val result = mockMvc!!.perform(
            get("/user/id/1")
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
    private fun obtainAccessToken(username: String, password: String): String {
        val params = LinkedMultiValueMap<String, String>()
        params.add("grant_type", "password")
        params.add("client_id", CLIENT_ID)
        params.add("username", username)
        params.add("password", password)

        val result = mockMvc!!.perform(
            post("/oauth/token")
                .params(params)
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(CONTENT_TYPE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(CONTENT_TYPE))

        val resultString: String = result.andReturn().response.contentAsString

        val jsonParser = JacksonJsonParser()
        return jsonParser.parseMap(resultString)["access_token"].toString()
    }


}