package com.junjunguo.backend.services.impl

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service

@Service
class TranslateService(
    private val messageSource: MessageSource
) {
    private val defaultMessage = "Exception.unexpected"

    fun translate(message: String) = translate(message, null)

    fun translate(
        message: String,
        vararg args: String?
    ) = messageSource.getMessage(message, args, defaultMessage, LocaleContextHolder.getLocale())!!
}