package dev.vincenzocostagliola.details.data.domain

import dev.vincenzocostagliola.details.data.dto.TodoDto
import org.threeten.bp.OffsetDateTime

internal data class Todo(
    val id : Int,
    val title : String,
    val description : String,
    val status : String,
    val addedDate : OffsetDateTime
){
    fun toDto(date: OffsetDateTime): TodoDto {
        return TodoDto(
            id = id,
            title = title,
            description = description,
            status = status,
            addedDate = date
        )
    }
}