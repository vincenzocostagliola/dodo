package dev.vincenzocostagliola.details.data.dto

import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.details.data.domain.Todo
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber

internal data class TodoDto(
    val id: Int?,
    val title: String,
    val description: String,
    val status: String,
    val addedDate: OffsetDateTime
) {
    fun toDomain(): Todo {
        return Todo(
            id = id,
            title = title,
            description = description,
            status = status.toDomain(),
            addedDate = addedDate
        )
    }

    fun toTodoDb(): TodoDb {
        return if (id != null) {
            TodoDb(
                title = title,
                description = description,
                addedDate = addedDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                status = status,
                id = id
            )
        } else {
            TodoDb(
                title = title,
                description = description,
                addedDate = addedDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                status = status
            )
        }
    }
}

private fun String.toDomain(): Todo.TodoStatus {
    val status = Todo.TodoStatus.entries.find { orderBy ->
        orderBy.name == this
    }
    Timber.d("DetailsScreen - TodoDto -  toDomain - $status")

    return status ?: Todo.TodoStatus.TOSTART
}
