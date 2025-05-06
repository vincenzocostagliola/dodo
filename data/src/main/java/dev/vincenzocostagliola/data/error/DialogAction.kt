package dev.vincenzocostagliola.data.error

import dev.vincenzocostagliola.data.R

sealed class DialogAction {

    abstract val stringIdResource: Int

    data object Retry : DialogAction() {
        override val stringIdResource: Int = R.string.retry
    }

    data object Quit : DialogAction() {
        override val stringIdResource: Int = R.string.logout
    }

    data object Leave : DialogAction() {
        override val stringIdResource: Int = R.string.leave
    }

}