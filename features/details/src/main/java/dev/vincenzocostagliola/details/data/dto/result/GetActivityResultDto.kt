package dev.vincenzocostagliola.details.data.dto.result

import dev.vincenzocostagliola.details.data.dto.TodoDto

internal sealed class GetActivityResultDto {
    data class Success(val todo: TodoDto) : GetActivityResultDto()
    data class Failure(val error: Throwable) : GetActivityResultDto()
    data object NotFound : GetActivityResultDto()
}