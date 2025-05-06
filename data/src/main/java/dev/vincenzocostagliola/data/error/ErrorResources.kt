package dev.vincenzocostagliola.data.error

import androidx.annotation.IdRes

data class ErrorResources(
    val errorTextResource: Int,
    val mainAction : DialogAction,
    val secondaryAction : DialogAction?
)