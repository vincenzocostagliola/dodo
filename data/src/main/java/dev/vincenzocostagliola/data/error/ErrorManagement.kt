package dev.vincenzocostagliola.data.error

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.retrofit.raw
import dev.vincenzocostagliola.data.error.CoinSwatchError
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class ErrorManagement @Inject constructor() {

    fun manageOnError(e: ApiResponse.Failure.Error): CoinSwatchError {
        Timber.Forest.d("CoinSwatchErrorManagement - manageError : $e")
        return manageHttpCodeError(e.raw.code)
    }

    fun manageOnException(e: ApiResponse.Failure.Exception): CoinSwatchError {
        Timber.Forest.d("CoinSwatchErrorManagement - manageOnException : $e")
        return evaluateCause(e.throwable.cause)
    }

    fun manageException(e: Throwable): CoinSwatchError {
        Timber.Forest.d("CoinSwatchErrorManagement - manageOnException : $e")
        return evaluateCause(e.cause)
    }

    private fun evaluateCause(cause: Throwable?): CoinSwatchError {
        Timber.Forest.d("CoinSwatchErrorManagement - evaluateCause : $cause")


        return when (cause) {
            is UnknownHostException -> {
                CoinSwatchError.ErrorOffline
            }

            is SSLHandshakeException,
            is SocketTimeoutException,
            is ConnectException -> {
                CoinSwatchError.ErrorConnection
            }

            else -> {
                CoinSwatchError.ErrorGenericCause
            }
        }
    }

    private fun manageHttpCodeError(errorCode: Int?): CoinSwatchError {
        Timber.Forest.d("CoinSwatchErrorManagement - Status : ${errorCode}")
        //TODO add specific error code mapping based on feature/module
        return when (errorCode) {
            400 -> {
                CoinSwatchError.ErrorBadRequest
            }

            401 -> {
                // Perform a logout
                CoinSwatchError.ErrorAndQuit
            }

            405 -> {
                CoinSwatchError.ErrorBadRequest
            }

            500 -> {
                CoinSwatchError.ErrorServerInternalError
            }

            504 -> {
                CoinSwatchError.ErrorTimeoutGateWay
            }

            else -> {
                CoinSwatchError.GenericError
            }
        }
    }
}