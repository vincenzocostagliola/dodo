package dev.vincenzocostagliola.data.datapersistence.data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingsDP(
    @SerialName("orderSelected") val orderSelected: OrderBy,
    @SerialName("possibleSelections") val possibleSelections: List<OrderBy>
) {
    enum class OrderBy {
        DATE,
        NAME,
        STATUS,
        REVERSED_DATE,
        REVERSED_NAME,
        REVERSED_STATUS
    }
}