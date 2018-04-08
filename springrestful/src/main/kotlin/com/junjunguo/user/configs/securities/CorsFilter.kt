package com.junjunguo.user.configs.securities

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//class CorsFilter : Filter {
//
//    @Throws(IOException::class, ServletException::class)
//    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
//        val response = res as HttpServletResponse
//        response.setHeader("Access-Control-Allow-Origin", "*")
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
//        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type")
//        response.setHeader("Access-Control-Max-Age", "3600")
//        if ("OPTIONS".equals((req as HttpServletRequest).method, ignoreCase = true)) {
//            response.status = HttpServletResponse.SC_OK
//        } else {
//            chain.doFilter(req, res)
//        }
//    }
//
//    override fun destroy() {}
//
//    @Throws(ServletException::class)
//    override fun init(config: FilterConfig) {
//    }
//
//}