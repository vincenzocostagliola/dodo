package dev.vincenzocostagliola.settings.data.domain

import dev.vincenzocostagliola.designsystem.composables.Option
import timber.log.Timber

data class SettingsDomain(
    val orderSelected: OrderBy
) {

    enum class OrderBy {
        DATE,
        NAME,
        STATUS,
        REVERSED_DATE,
        REVERSED_NAME,
        REVERSED_STATUS,
        NOT_ORDERED
    }

    companion object {
        fun Option.toDomain(): SettingsDomain {
            val selected = OrderBy.entries.find { orderBy ->
                orderBy.name == this.value
            }
            Timber.d("SettingScreen - SettingsDomain - toDomain: $selected")
            return SettingsDomain(orderSelected = selected ?: OrderBy.NOT_ORDERED)
        }
    }
}
