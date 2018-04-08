package com.junjunguo.user.utillities

import com.junjunguo.user.system.errorHanlder.ApiErrorModel
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletRequest

class ErrorHandlerUtil {
    companion object {
        fun getApiError(ex: Exception, request: WebRequest, status: HttpStatus, showErrors: Boolean): ApiErrorModel {
            return ApiErrorModel(
                status.value(),
                status.reasonPhrase,
                ex.message!!,
                if (showErrors) listOf(
                    "request Description: " + request.getDescription(true),
                    "localizedMessage: " + ex.localizedMessage,
                    "exception: " + ex.toString(),
                    "cause: " + ex.cause
                ) else null
            )
        }

        fun getApiError(ex: Exception, request: HttpServletRequest, status: HttpStatus, showErrors: Boolean): ApiErrorModel {
            return ApiErrorModel(
                status.value(),
                status.reasonPhrase,
                ex.message!!,
                if (showErrors) listOf(
                    "request Description: " + request.contextPath,
                    "localizedMessage: " + ex.localizedMessage,
                    "exception: " + ex.toString(),
                    "cause: " + ex.cause
                ) else null
            )
        }
    }
}
