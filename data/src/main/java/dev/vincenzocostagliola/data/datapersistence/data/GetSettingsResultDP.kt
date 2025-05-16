package dev.vincenzocostagliola.data.datapersistence.data

sealed class GetSettingsResultDP {
    data class Success(val dto: SettingsDP?) : GetSettingsResultDP()
    data class Failure(val error: Throwable) : GetSettingsResultDP()
}