package com.example.playground.data.db

sealed class ResponseModel<out T>(val success: Boolean, val model: T? = null, val error: String? = null){
    data class GET<out T>(val s: Boolean, val m: T? = null, val e: String? = null) : ResponseModel<T>(s, m, e)
    data class INSERT<out T>(val s: Boolean, val m: T? = null, val e: String? = null) : ResponseModel<T>(s, m, e)
    data class UPDATE<out T>(val s: Boolean, val m: T? = null, val e: String? = null) : ResponseModel<T>(s, m, e)
    data class DELETE<out T>(val s: Boolean, val m: T? = null, val e: String? = null) : ResponseModel<T>(s, m, e)
    data class RESTORE<out T>(val s: Boolean, val m: T? = null, val e: String? = null) : ResponseModel<T>(s, m, e)
}
