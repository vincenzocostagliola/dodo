package dev.vincenzocostagliola.data.error

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.retrofit.raw
import com.skydoves.sandwich.retrofit.statusCode
import timber.log.Timber

fun ApiResponse.Failure.Error.logErrorBasedOnCode(netCallId : String, request : String) {
        val errorCodeString = this.statusCode.code.toString()
        when {
            errorCodeString.startsWith("4") -> Timber.e("$netCallId " +
                    "- errorCode: ${this.raw.code} " +
                    "- error: ${this.raw} " +
                    "- request: ${request.toString()}")

            errorCodeString.startsWith("5") -> Timber.w("$netCallId " +
                    "- errorCode: ${this.raw.code} " +
                    "- error: ${this.raw} " +
                    "- request: ${request.toString()}")

            else -> Timber.e("$netCallId " +
                    "- errorCode: ${this.raw.code} " +
                    "- error: ${this.raw} " +
                    "- request: ${request.toString()}")
        }
    }