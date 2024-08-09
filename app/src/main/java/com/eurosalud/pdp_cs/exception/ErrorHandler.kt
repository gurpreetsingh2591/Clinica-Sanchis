package com.eurosalud.pdp_cs.exception

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

class  ErrorHandler {
    fun reportError(error: Throwable): String? {
        return if (error is HttpException) {
            when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> com.eurosalud.pdp_cs.annotation.Constants.UN_AUTH_USER
                HttpsURLConnection.HTTP_FORBIDDEN -> com.eurosalud.pdp_cs.annotation.Constants.FORBIDDEN
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> com.eurosalud.pdp_cs.annotation.Constants.INTERNAL_ERROR
                HttpsURLConnection.HTTP_BAD_REQUEST -> com.eurosalud.pdp_cs.annotation.Constants.BAD_REQUEST
                else -> error.getLocalizedMessage()
            }
        } else if (error is WrapperError) {
            error.message
        } else if (error is JsonSyntaxException) {
            com.eurosalud.pdp_cs.annotation.Constants.API_NOT_RESPONDING
        } else if (error is SocketTimeoutException) {
            com.eurosalud.pdp_cs.annotation.Constants.CANNOT_CONNECT_TO_SERVER
        }  else if (error is UnknownHostException) {
            com.eurosalud.pdp_cs.annotation.Constants.CANNOT_CONNECT_TO_SERVER
        } else {
            error.message
        }
    }

    fun reportError(errorCode: Int): String {
        var errorMessage = ""
        when (errorCode) {
            HttpsURLConnection.HTTP_UNAUTHORIZED -> errorMessage = com.eurosalud.pdp_cs.annotation.Constants.UN_AUTH_USER
            HttpsURLConnection.HTTP_FORBIDDEN -> errorMessage = com.eurosalud.pdp_cs.annotation.Constants.FORBIDDEN
            HttpsURLConnection.HTTP_INTERNAL_ERROR -> errorMessage = com.eurosalud.pdp_cs.annotation.Constants.INTERNAL_ERROR
            HttpsURLConnection.HTTP_BAD_REQUEST -> errorMessage = com.eurosalud.pdp_cs.annotation.Constants.BAD_REQUEST
        }
        return errorMessage
    }
}