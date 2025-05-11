package dev.vincenzocostagliola.home.data.dto.result

import dev.vincenzocostagliola.home.data.dto.TodoDto

internal sealed class GetActivityResultDto {
    data class Success(val list: List<TodoDto>) : GetActivityResultDto()
    data class Failure(val error: Throwable) : GetActivityResultDto()
}