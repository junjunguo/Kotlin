//package com.junjunguo.backend.settings.errorHanlder
//
//class CheckUtil {
//    companion object {
//
//        fun isNull(`object`: Any?, message: String, vararg args: Any) {
//            if (`object` != null) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun isTrue(expression: Boolean, message: String, vararg args: Any) {
//            if (!expression) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notNull(`object`: Any?, message: String, vararg args: Any) {
//            if (`object` == null) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notEmpty(array: Array<Any>, message: String, vararg args: Any) {
//            if (ObjectUtils.isEmpty(array)) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notEmpty(collection: Collection<*>, message: String, vararg args: Any) {
//            if (CollectionUtils.isEmpty(collection)) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notEmpty(map: Map<*, *>, message: String, vararg args: Any) {
//            if (CollectionUtils.isEmpty(map)) {
//                throw RestException(message, args)
//            }
//        }
//
//        fun notEmpty(text: String?, message: String, vararg args: Any) {
//            if (text == null || "" == text.trim { it <= ' ' }) {
//                throw RestException(message, args)
//            }
//        }
//    }
//}
