package com.junjunguo.backend.settings.errorHanlder

import com.junjunguo.backend.settings.errorHanlder.exceptions.BadRequestException
import com.junjunguo.backend.settings.errorHanlder.exceptions.InternalServerException
import com.junjunguo.backend.settings.errorHanlder.exceptions.NotFoundException
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.util.WebUtils


@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @Value("\${app_config.show_errors_for_debug}")
    private var showErrors: Boolean = false

    private val log = LogFactory.getLog(this.javaClass)

    @Autowired
    private lateinit var messageSource: MessageSource

    override fun handleExceptionInternal(
        ex: Exception, @Nullable body: Any?,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {

        if (HttpStatus.INTERNAL_SERVER_ERROR == status) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST)
        }
        log.error(ex.localizedMessage, ex)
        return ResponseEntity(ErrorHandlerUtil.getApiError(ex, request, status, showErrors), headers, status)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        log.error(ex.localizedMessage, ex.cause)
        return ResponseEntity(
            ErrorHandlerUtil.getApiError(
                ex,
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                showErrors,
                messageSource.getMessage("Exception.unexpected", null, request.locale)
            ),
            HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(InternalServerException::class)
    fun handleAll(ex: InternalServerException, request: WebRequest): ResponseEntity<Any> {
        log.error(ex.localizedMessage, ex.cause)
        return ResponseEntity(
            ErrorHandlerUtil.getApiError(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, showErrors),
            HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<Any> {
        log.debug(ex.localizedMessage, ex.cause)
        return ResponseEntity(
            ErrorHandlerUtil.getApiError(ex, request, HttpStatus.BAD_REQUEST, showErrors),
            HttpHeaders(),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<Any> {
        log.debug(ex.localizedMessage, ex.cause)
        return ResponseEntity(
            ErrorHandlerUtil.getApiError(ex, request, HttpStatus.NOT_FOUND, showErrors),
            HttpHeaders(),
            HttpStatus.NOT_FOUND
        )
    }
}