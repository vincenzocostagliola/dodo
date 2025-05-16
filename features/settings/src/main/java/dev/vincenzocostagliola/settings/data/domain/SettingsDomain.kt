package dev.vincenzocostagliola.settings.data.domain

data class SettingsDomain(
    val orderSelected: OrderBy,
    val possibleSelections: List<OrderBy>
){

    enum class OrderBy {
        DATE,
        NAME,
        STATUS,
        REVERSED_DATE,
        REVERSED_NAME,
        REVERSED_STATUS
    }
}
