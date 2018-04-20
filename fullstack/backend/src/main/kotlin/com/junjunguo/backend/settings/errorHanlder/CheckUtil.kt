//package com.junjunguo.backend.settings.errorHanlder
//
//import com.junjunguo.backend.settings.errorHanlder.exceptions.RestException
//import org.springframework.util.CollectionUtils
//import org.springframework.util.ObjectUtils
//
//class CheckUtil {
//    companion object {
//
//        fun isNull(obj: Any?, message: String, args: Array<Any>?) {
//            if (obj != null) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun isTrue(expression: Boolean, message: String, args: Array<Any>?) {
//            if (!expression) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notNull(obj: Any?, message: String, args: Array<Any>?) {
//            if (obj == null) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notEmpty(array: Array<Any>, message: String, args: Array<Any>?) {
//            if (ObjectUtils.isEmpty(array)) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notEmpty(collection: Collection<*>, message: String, args: Array<Any>?) {
//            if (CollectionUtils.isEmpty(collection)) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notEmpty(map: Map<*, *>, message: String, args: Array<Any>?) {
//            if (CollectionUtils.isEmpty(map)) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notEmpty(text: String?, message: String, args: Array<Any>?) {
//            if (text == null || "" == text.trim { it <= ' ' }) {
//                throw RestException(message, args)
//            }
//        }
//    }
//}
