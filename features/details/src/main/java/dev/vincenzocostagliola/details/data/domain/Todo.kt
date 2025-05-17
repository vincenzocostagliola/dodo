package dev.vincenzocostagliola.details.data.domain

import dev.vincenzocostagliola.designsystem.composables.FieldForm
import dev.vincenzocostagliola.designsystem.composables.InfoForm
import dev.vincenzocostagliola.designsystem.composables.Option
import dev.vincenzocostagliola.details.data.dto.TodoDto
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

internal data class Todo(
    val id: Int,
    val title: String,
    val description: String,
    val status: TodoStatus,
    val addedDate: OffsetDateTime
) {
    enum class TodoStatus {
        TOSTART,
        STARTED,
        STOPPED,
        FINISHED
    }




    fun toDto(date: OffsetDateTime): TodoDto {
        return TodoDto(
            id = id,
            title = title,
            description = description,
            status = status.name,
            addedDate = date
        )
    }

    fun toInfoForm(readOnly: Boolean): InfoForm {
        val updated = InfoForm(
            id = id,
            readOnly = isInError(title).not()
                    && isInError(description).not(),
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
                )
            ),
            addedDate = addedDate,
            statusOptions =  TodoStatus.entries.map { status -> Option(
                value = status.name,
                isSelected = status == this.status,
                isClickable = !readOnly
            ) }
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
                status = statusOptions
                    .filter { it.isSelected }
                    .map { option ->
                        TodoStatus.entries
                            .firstOrNull { option.value == it.name }
                    }.firstOrNull() ?: TodoStatus.TOSTART,
                addedDate = addedDate
            )
        }

        fun TodoStatus.toOption() : Option = Option(
            value = this.name,
            isSelected = false,
            isClickable = true
        )

        fun getOptionList() : List<Option>{
            return Todo.TodoStatus.entries.map { it.toOption() }
        }
    }
}