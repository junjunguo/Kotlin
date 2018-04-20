//package com.junjunguo.backend.settings.errorHanlder
//
//import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor
//import org.springframework.web.bind.support.WebDataBinderFactory
//import org.springframework.web.context.request.NativeWebRequest
//import org.springframework.web.method.support.ModelAndViewContainer
//import org.springframework.core.MethodParameter
//
//
//
//class DTOModelMapper: RequestResponseBodyMethodProcessor() {
//
//
//    @Throws(Exception::class)
//    override fun resolveArgument(
//        parameter: MethodParameter,
//        mavContainer: ModelAndViewContainer?,
//        webRequest: NativeWebRequest,
//        binderFactory: WebDataBinderFactory?
//    ): Any {
//        val dto = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory)
//        val id = getEntityId(dto)
//        if (id == null) {
//            return modelMapper.map(dto, parameter.parameterType)
//        } else {
//            val persistedObject = entityManager.find(parameter.parameterType, id)
//            CheckUtil.notNull(
//                persistedObject, "Exception.notFound",
//                parameter.parameterType.simpleName, id
//            )
//            modelMapper.map(dto, persistedObject)
//            return persistedObject
//        }
//    }
//}