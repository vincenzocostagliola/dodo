package dev.vincenzocostagliola.data.datapersistence


interface DataPersistence {
    suspend fun getSettings(): SettingsDP?
}

data class SettingsDP(
    val pippo: Int
)
