package com.junjunguo.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.junjunguo.user.models.api.UserRegisterModel
import com.junjunguo.user.system.errorHanlder.ApiError
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@RunWith(SpringRunner::class)
@SpringBootTest
class ControllerTests {

    @Value("\${app_config.show_errors_for_debug}")
    private var showErrors: Boolean = false

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Test
    fun testExceptionResponse() {
        val mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/user/register", UserRegisterModel("", "111111")))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn()

        val apiError = ObjectMapper().findAndRegisterModules().readValue<ApiError>(result.response.contentAsString)

        assert(apiError.status == HttpStatus.BAD_REQUEST.value())
        assert((showErrors && apiError.errors?.size!! > 0) || (!showErrors && apiError.errors == null))

    }
}