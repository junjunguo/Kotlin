package com.junjunguo.backend.settings.errorHanlder

import com.junjunguo.backend.models.api.RestMessage
import com.junjunguo.backend.settings.errorHanlder.exceptions.RestException
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*


@ControllerAdvice
class RestExceptionHandler(var messageSource: MessageSource) {
//class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @Value("\${app_config.show_errors_for_debug}")
    private var showErrors: Boolean = false

    private val log = LogFactory.getLog(this.javaClass)

    private val UNEXPECTED_ERROR = "Exception.unexpected"

//    @Autowired
//    private val messageSource: MessageSource

//    @Autowired
//    fun RestExceptionHandler(messageSource: MessageSource): ??? {
//        this.messageSource = messageSource
//    }

    @ExceptionHandler(RestException::class)
    fun handleIllegalArgument(ex: RestException, locale: Locale): ResponseEntity<RestMessage> {
        val errorMessage = messageSource.getMessage(ex.message!!, ex.args!!, locale)
        return ResponseEntity(RestMessage(errorMessage), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        locale: Locale
    ): ResponseEntity<RestMessage> {
        val result = ex.bindingResult
        val errorMessages = result.allErrors
            .map { objectError -> messageSource.getMessage(objectError, locale) }
        return ResponseEntity(RestMessage(errorMessages), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception, locale: Locale): ResponseEntity<RestMessage> {
        val errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale)
        log.error(ex.printStackTrace())
        return ResponseEntity(RestMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR)
    }
//    override fun handleExceptionInternal(
//        ex: Exception, @Nullable body: Any?,
//        headers: HttpHeaders, status: HttpStatus, request: WebRequest
//    ): ResponseEntity<Any> {
//
//        if (HttpStatus.INTERNAL_SERVER_ERROR == status) {
//            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST)
//        }
//        log.error(ex.localizedMessage, ex)
//        return ResponseEntity(ErrorHandlerUtil.getApiError(ex, request, status, showErrors), headers, status)
//    }
//
//    @ExceptionHandler(Exception::class)
//    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<Any> {
//        log.error(ex.localizedMessage, ex.cause)
//        return ResponseEntity(
//            ErrorHandlerUtil.getApiError(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, showErrors),
//            HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
//        )
//    }
//
//    @ExceptionHandler(InternalServerException::class)
//    fun handleAll(ex: InternalServerException, request: WebRequest): ResponseEntity<Any> {
//        log.error(ex.localizedMessage, ex.cause)
//        return ResponseEntity(
//            ErrorHandlerUtil.getApiError(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, showErrors),
//            HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
//        )
//    }
//
//    @ExceptionHandler(BadRequestException::class)
//    fun handleAll(ex: BadRequestException, request: WebRequest): ResponseEntity<Any> {
//        log.debug(ex.localizedMessage, ex.cause)
//        return ResponseEntity(
//            ErrorHandlerUtil.getApiError(ex, request, HttpStatus.BAD_REQUEST, showErrors),
//            HttpHeaders(),
//            HttpStatus.BAD_REQUEST
//        )
//    }
//
//    @ExceptionHandler(NotFoundException::class)
//    fun handleAll(ex: NotFoundException, request: WebRequest): ResponseEntity<Any> {
//        log.debug(ex.localizedMessage, ex.cause)
//        return ResponseEntity(
//            ErrorHandlerUtil.getApiError(ex, request, HttpStatus.NOT_FOUND, showErrors),
//            HttpHeaders(),
//            HttpStatus.NOT_FOUND
//        )
//    }
}