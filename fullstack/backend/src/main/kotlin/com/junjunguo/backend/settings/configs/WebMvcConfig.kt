package com.junjunguo.backend.settings.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*
import java.util.Locale


@Configuration
class WebMvcConfig : WebMvcConfigurer {

    private val locales = Arrays.asList(
        Locale.ENGLISH,
        Locale.CHINESE
    )

    @Autowired
    private lateinit var properties: WebMvcProperties

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    @Bean
    fun localeResolver(): LocaleResolver {
//        val slr = SessionLocaleResolver()
//        slr.setDefaultLocale(Locale.ENGLISH)
//        return slr
        val resolver = AcceptHeaderLocaleResolver()
        resolver.supportedLocales = locales
        resolver.defaultLocale = properties.locale
        return resolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val lci = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }
}