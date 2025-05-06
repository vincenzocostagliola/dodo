package dev.vincenzocostagliola.home.data.domain.result

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.home.data.domain.ActivityDomain

internal sealed class GetActivityResult {
    data class Success(val list: List<ActivityDomain>) : GetActivityResult()
    data class Failure(val error: AppError) : GetActivityResult()
}