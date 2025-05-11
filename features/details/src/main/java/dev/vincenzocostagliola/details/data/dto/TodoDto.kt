package dev.vincenzocostagliola.details.data.dto

import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.details.data.domain.Todo
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

internal data class TodoDto(
    val id : Int,
    val title : String,
    val description : String,
    val status : String,
    val addedDate : OffsetDateTime
){
    fun toDomain() : Todo {
        return Todo(
            id = id,
            title = title,
            description = description,
            status = status
        )
    }

    fun toTodoDb(): TodoDb {
        return TodoDb(
            title = title,
            description = description,
            addedDate = addedDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            status = status
        )
    }
}
