package dev.vincenzocostagliola.settings.data.dto

import dev.vincenzocostagliola.data.datapersistence.data.SettingsDP
import dev.vincenzocostagliola.settings.data.domain.SettingsDomain
import dev.vincenzocostagliola.settings.data.domain.SettingsDomain.OrderBy
import timber.log.Timber

internal data class SettingsDto(
    val orderSelected: String
) {

    fun toDomain(): SettingsDomain {
        val selected = OrderBy.entries.find { orderBy ->
            orderBy.name == this.orderSelected
        }
        Timber.d("SettingScreen - SettingsDto - toDomain: $selected")
        return SettingsDomain(orderSelected = selected ?: OrderBy.NOT_ORDERED)
    }


    companion object {
        fun SettingsDP?.toDTO(): SettingsDto? {
            return if (this != null) {
                SettingsDto(
                    orderSelected = orderSelected
                )
            } else {
                null
            }
        }
    }
}
