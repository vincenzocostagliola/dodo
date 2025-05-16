package dev.vincenzocostagliola.settings.data.dto

import dev.vincenzocostagliola.data.datapersistence.data.SettingsDP

data class SettingsDto(
    val orderSelected: OrderBy,
    val possibleSelections: List<OrderBy>
) {
    enum class OrderBy {
        DATE,
        NAME,
        STATUS,
        REVERSED_DATE,
        REVERSED_NAME,
        REVERSED_STATUS
    }

    companion object {
        fun SettingsDP?.toDTO(): SettingsDto? {
            return if (this != null) {
                SettingsDto(
                    orderSelected = orderSelected.toDTO(),
                    possibleSelections = possibleSelections.map { it.toDTO() }
                )
            } else {
                null
            }
        }


        private fun SettingsDP.OrderBy.toDTO(): OrderBy {
            return when (this) {
                SettingsDP.OrderBy.DATE -> OrderBy.DATE
                SettingsDP.OrderBy.NAME -> OrderBy.NAME
                SettingsDP.OrderBy.STATUS -> OrderBy.STATUS
                SettingsDP.OrderBy.REVERSED_DATE -> OrderBy.REVERSED_DATE
                SettingsDP.OrderBy.REVERSED_NAME -> OrderBy.REVERSED_NAME
                SettingsDP.OrderBy.REVERSED_STATUS -> OrderBy.REVERSED_STATUS
            }
        }
    }

}
