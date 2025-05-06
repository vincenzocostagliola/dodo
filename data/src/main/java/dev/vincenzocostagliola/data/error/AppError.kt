package dev.vincenzocostagliola.data.error

import dev.vincenzocostagliola.data.R
import dev.vincenzocostagliola.data.error.DialogAction.Leave
import dev.vincenzocostagliola.data.error.DialogAction.Quit
import dev.vincenzocostagliola.data.error.DialogAction.Retry

sealed class AppError {
    abstract val newResources: ErrorResources

    data object GenericError : AppError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.unknown_error,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorBadRequest : AppError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.unknown_error,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorAndQuit : AppError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.not_allowed,
            mainAction = Quit,
            secondaryAction = null
        )
    }

    object ErrorServerInternalError : AppError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.not_allowed,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorTimeoutGateWay : AppError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.not_allowed,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorConnection : AppError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.offline,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorGenericCause : AppError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.unknown_error,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorOffline : AppError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.offline,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }
}