package dev.vincenzocostagliola.details.data.domain

import dev.vincenzocostagliola.designsystem.composables.FieldForm
import dev.vincenzocostagliola.designsystem.composables.InfoForm
import dev.vincenzocostagliola.details.data.dto.TodoDto
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

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
        val updated =  InfoForm(
            id = id,
            readOnly = isInError(title).not()
                && isInError(description).not()
                && isInError(status).not(),
            list = listOf(
                FieldForm.Title(
                    text = title,
                    singleLine = false,
                    isError = isInError(title)
                ),
                FieldForm.Description(
                    text = description,
                    singleLine = false,
                    isError = isInError(description)
                ),
                FieldForm.Status(
                    text = status,
                    singleLine = false,
                    isError = isInError(status)
                )
            ),
            addedDate = addedDate
        )

        Timber.d("Todo - toInfoForm - updated : $updated")
        return updated
    }

    private fun isInError(text: String): Boolean {
        val inError = text.isBlank() || text.isEmpty()
        Timber.d("Todo - isInError - : $inError - text: $text")
        return inError
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