package dev.vincenzocostagliola.home

import dev.vincenzocostagliola.home.data.dto.TodoDto
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

internal fun createTodoDtoList(): List<TodoDto> {
    val list: List<TodoDto> = listOf(
        TodoDto(
            id = 9091,
            title = "consectetuer",
            description = "cubilia",
            status = "aenean",
            addedDate = OffsetDateTime.now()
        ),
        TodoDto(
            id = 9092,
            title = "consectetuer",
            description = "cubilia",
            status = "aenean",
            addedDate = OffsetDateTime.now().plusDays(1)
        ),
        TodoDto(
            id = 9093,
            title = "consectetuer",
            description = "cubilia",
            status = "aenean",
            addedDate = OffsetDateTime.now().plusDays(2)
        ),
        TodoDto(
            id = 9091,
            title = "consectetuer",
            description = "cubilia",
            status = "aenean",
            addedDate = OffsetDateTime.now().plusDays(2)
        ),
        TodoDto(
            id = 9091,
            title = "consectetuer",
            description = "cubilia",
            status = "aenean",
            addedDate = OffsetDateTime.now().plusDays(3)
        ),
        TodoDto(
            id = 9091,
            title = "consectetuer",
            description = "cubilia",
            status = "aenean",
            addedDate = OffsetDateTime.now().plusDays(4)
        ),
    )
    Timber.d("HomeScreen - Repository -  getAllActivities  - fakeList: $list")
    return list
}