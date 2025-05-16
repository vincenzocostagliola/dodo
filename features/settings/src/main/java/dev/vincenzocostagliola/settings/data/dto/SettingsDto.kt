package dev.vincenzocostagliola.settings.data.dto

data class SettingsDto(
    val orderSelected : OrderBy,
    val possibleSelections : List<OrderBy>
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
