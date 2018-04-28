package com.junjunguo.backend.settings.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*
import java.util.Locale


@Configuration
@EnableWebMvc
class WebMvcConfig : WebMvcConfigurer {

    private val locales = Arrays.asList(
        Locale.ENGLISH,
        Locale.CHINESE
    )

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = AcceptHeaderLocaleResolver()
        resolver.supportedLocales = locales
        resolver.defaultLocale = Locale.ENGLISH
        return resolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val lci = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }
}