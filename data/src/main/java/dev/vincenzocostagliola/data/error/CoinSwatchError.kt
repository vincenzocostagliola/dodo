package dev.vincenzocostagliola.data.error

import dev.vincenzocostagliola.data.R
import dev.vincenzocostagliola.data.error.DialogAction.Leave
import dev.vincenzocostagliola.data.error.DialogAction.Quit
import dev.vincenzocostagliola.data.error.DialogAction.Retry

sealed class CoinSwatchError {
    abstract val newResources: ErrorResources

    data object GenericError : CoinSwatchError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.unknown_error,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorBadRequest : CoinSwatchError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.unknown_error,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorAndQuit : CoinSwatchError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.not_allowed,
            mainAction = Quit,
            secondaryAction = null
        )
    }

    object ErrorServerInternalError : CoinSwatchError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.not_allowed,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorTimeoutGateWay : CoinSwatchError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.not_allowed,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorConnection : CoinSwatchError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.offline,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorGenericCause : CoinSwatchError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.unknown_error,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }

    object ErrorOffline : CoinSwatchError() {
        override val newResources: ErrorResources = ErrorResources(
            errorTextResource = R.string.offline,
            mainAction = Retry,
            secondaryAction = Leave
        )
    }
}