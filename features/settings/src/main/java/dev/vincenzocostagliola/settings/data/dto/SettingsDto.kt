package dev.vincenzocostagliola.settings.data.dto

import dev.vincenzocostagliola.data.datapersistence.data.SettingsDP
import dev.vincenzocostagliola.settings.data.domain.SettingsDomain

internal data class SettingsDto(
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

    fun toDomain(): SettingsDomain? =
        SettingsDomain(
            orderSelected = orderSelected.toDomain(),
            possibleSelections = possibleSelections.map { it.toDomain() }
        )

    private fun SettingsDto.OrderBy.toDomain(): SettingsDomain.OrderBy {
        return when (this) {
            OrderBy.DATE -> SettingsDomain.OrderBy.DATE
            OrderBy.NAME -> SettingsDomain.OrderBy.NAME
            OrderBy.STATUS -> SettingsDomain.OrderBy.STATUS
            OrderBy.REVERSED_DATE -> SettingsDomain.OrderBy.REVERSED_DATE
            OrderBy.REVERSED_NAME -> SettingsDomain.OrderBy.REVERSED_NAME
            OrderBy.REVERSED_STATUS -> SettingsDomain.OrderBy.REVERSED_STATUS
        }
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
