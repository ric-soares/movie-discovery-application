package com.example.moviediscoveryapplication.common.exceptions

import retrofit2.HttpException

sealed class NetworkExceptions(errorCode: Int?, message: String) : Exception("error code: $errorCode -> message: $message") {

    class ClientErrorException(errorCode: Int, message: String) : NetworkExceptions(errorCode, message)
    class ServerErrorException(errorCode: Int, message: String) : NetworkExceptions(errorCode, message)
    class OtherReasonException(errorCode: Int, message: String) : NetworkExceptions(errorCode, message)
    class NoConnectivityException(message: String) : NetworkExceptions(null, message)

    companion object {
        fun fromHttpException(error: HttpException): NetworkExceptions {
            return when (error.code()) {
                in 400..499 -> handleClientError(error.code())
                in 500..599 -> handleServerError(error.code())
                else -> handleOtherReasonError(error.code())
            }
        }

        private fun handleClientError(errorCode: Int): NetworkExceptions {
            return when (errorCode) {
                400 -> ClientErrorException(errorCode, "Bad Request")
                401 -> ClientErrorException(errorCode, "UnauthorizedException")
                403 -> ClientErrorException(errorCode, "ForbiddenException")
                404 -> ClientErrorException(errorCode, "NotFoundException")
                else -> ClientErrorException(errorCode, "Unknown Client Error")
            }
        }

        private fun handleServerError(errorCode: Int): NetworkExceptions {
            return when (errorCode) {
                500 -> ServerErrorException(errorCode, "Internal Server Error")
                501 -> ServerErrorException(errorCode, "Not Implemented")
                502 -> ServerErrorException(errorCode, "Bad Gateway")
                503 -> ServerErrorException(errorCode, "Service Unavailable")
                504 -> ServerErrorException(errorCode, "Gateway Timeout")
                505 -> ServerErrorException(errorCode, "HTTP Version Not Supported")
                else -> ServerErrorException(errorCode, "Unknown Server Error")
            }
        }

        private fun handleOtherReasonError(errorCode: Int): NetworkExceptions {
            return OtherReasonException(errorCode, "Other Error")
        }

        fun noConnectivityException(): NetworkExceptions {
            return NoConnectivityException("No internet connection available")
        }

    }
}
