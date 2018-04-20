//package com.junjunguo.backend.settings.errorHanlder
//
//import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
//import java.util.*
//import javax.servlet.http.HttpServletRequest
//
//
//class AppAcceptHeaderLocaleResolver : AcceptHeaderLocaleResolver() {
//
//    private val locales = Arrays.asList(
//        Locale("en"),
//        Locale("zh")
//    )
//
//    @Override
//    fun resolveLocale(request: HttpServletRequest): Locale {
//
//        if (StringUtils.isBlank(request.getHeader("Accept-Language"))) {
//            return Locale.getDefault()
//        }
//        val list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"))
//        return Locale.lookup(list, locales)
//    }
//
//}