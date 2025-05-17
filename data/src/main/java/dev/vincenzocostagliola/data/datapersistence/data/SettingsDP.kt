package dev.vincenzocostagliola.data.datapersistence.data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingsDP(
    @SerialName("orderSelected") val orderSelected: String
)