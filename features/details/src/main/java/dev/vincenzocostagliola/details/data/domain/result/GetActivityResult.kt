package dev.vincenzocostagliola.details.data.domain.result

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.details.data.domain.Todo

internal sealed class GetActivityResult {
    data object NotFound : GetActivityResult()
    data class Success(val todo: Todo) : GetActivityResult()
    data class Failure(val error: AppError) : GetActivityResult()
}