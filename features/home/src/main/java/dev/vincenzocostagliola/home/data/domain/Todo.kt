package dev.vincenzocostagliola.home.data.domain

import org.threeten.bp.OffsetDateTime

data class Todo(
    val id : Int,
    val title : String,
    val description : String,
    val status : String,
    val addedDate : OffsetDateTime
)