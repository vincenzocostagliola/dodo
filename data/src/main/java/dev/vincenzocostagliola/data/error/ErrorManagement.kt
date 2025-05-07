package dev.vincenzocostagliola.data.error

import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class ErrorManagement @Inject constructor() {

    fun manageException(e: Throwable): AppError {
        Timber.Forest.d("AppErrorManagement - manageOnException : $e")
        return evaluateCause(e.cause)
    }

    private fun evaluateCause(cause: Throwable?): AppError {
        Timber.Forest.d("AppErrorManagement - evaluateCause : $cause")


        return when (cause) {
            is UnknownHostException -> {
                AppError.ErrorOffline
            }

            is SSLHandshakeException,
            is SocketTimeoutException,
            is ConnectException -> {
                AppError.ErrorConnection
            }

            else -> {
                AppError.ErrorGenericCause
            }
        }
    }

    private fun manageHttpCodeError(errorCode: Int?): AppError {
        Timber.Forest.d("AppErrorManagement - Status : ${errorCode}")
        //TODO add specific error code mapping based on feature/module
        return when (errorCode) {
            400 -> {
                AppError.ErrorBadRequest
            }

            401 -> {
                // Perform a logout
                AppError.ErrorAndQuit
            }

            405 -> {
                AppError.ErrorBadRequest
            }

            500 -> {
                AppError.ErrorServerInternalError
            }

            504 -> {
                AppError.ErrorTimeoutGateWay
            }

            else -> {
                AppError.GenericError
            }
        }
    }
}