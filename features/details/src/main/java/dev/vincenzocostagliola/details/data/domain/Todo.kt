package dev.vincenzocostagliola.details.data.domain

import dev.vincenzocostagliola.designsystem.composables.FieldForm
import dev.vincenzocostagliola.designsystem.composables.InfoForm
import dev.vincenzocostagliola.details.data.dto.TodoDto
import org.threeten.bp.OffsetDateTime

internal data class Todo(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val addedDate: OffsetDateTime
) {
    fun toDto(date: OffsetDateTime): TodoDto {
        return TodoDto(
            id = id,
            title = title,
            description = description,
            status = status,
            addedDate = date
        )
    }

    fun toInfoForm(readOnly: Boolean): InfoForm {
        return InfoForm(
            id = id,
            readOnly = readOnly,
            list = listOf(
                FieldForm.Title(
                    text = title,
                    singleLine = false,
                    isError = checkIfIsInError(title)
                ),
                FieldForm.Description(
                    text = description,
                    singleLine = false,
                    isError = checkIfIsInError(description)
                ),
                FieldForm.Status(
                    text = status,
                    singleLine = false,
                    isError = checkIfIsInError(status)
                )
            ), addedDate = addedDate
        )
    }

    private fun checkIfIsInError(text: String): Boolean {
        return text.isNotBlank() && text.isEmpty()
    }

    companion object {
        fun InfoForm.toTodo(): Todo {
            return Todo(
                id = id,
                title = list.first { it::class == FieldForm.Title::class }.text,
                description = list.first { it::class == FieldForm.Description::class }.text,
                status = list.first { it::class == FieldForm.Status::class }.text,
                addedDate = addedDate
            )
        }
    }
}