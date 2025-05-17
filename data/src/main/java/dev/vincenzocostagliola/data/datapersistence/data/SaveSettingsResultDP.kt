package dev.vincenzocostagliola.data.datapersistence.data

sealed class SaveSettingsResultDP {
    data object Success : SaveSettingsResultDP()
    data class Failure(val error: Throwable) : SaveSettingsResultDP()
}