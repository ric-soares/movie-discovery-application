package com.example.moviediscoveryapplication.common.exceptions

sealed class NetworkExceptions(message: String) : Exception(message) {
    class BadRequestException : NetworkExceptions("Bad Request")
    class UnauthorizedException : NetworkExceptions("Unauthorized")
    class ForbiddenException : NetworkExceptions("Forbidden")
    class NotFoundException : NetworkExceptions("Not Found")
    class HttpErrorException(errorCode: Int) : NetworkExceptions("HTTP Error: $errorCode")
}
