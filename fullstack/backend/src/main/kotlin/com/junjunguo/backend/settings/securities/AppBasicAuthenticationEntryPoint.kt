package com.junjunguo.backend.settings.securities

import com.junjunguo.backend.settings.errorHanlder.ErrorHandlerUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AppBasicAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

    @Value("\${app_config.show_errors_for_debug}")
    private var showErrors: Boolean = false

    @Value("\${security.security-realm}")
    private lateinit var securityRealm: String

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        realmName = securityRealm
        super.afterPropertiesSet()
    }

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest, response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.addHeader("WWW-Authenticate", "Basic realm=\"$realmName\"")
        response.sendError(
            HttpStatus.UNAUTHORIZED.value(),
            ErrorHandlerUtil.getApiError(authException, request, HttpStatus.UNAUTHORIZED, showErrors).toString()
        )
    }
}