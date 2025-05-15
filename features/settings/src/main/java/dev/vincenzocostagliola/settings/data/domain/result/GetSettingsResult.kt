package dev.vincenzocostagliola.settings.data.domain.result

import dev.vincenzocostagliola.settings.data.dto.SettingsDto

internal sealed class GetSettingsResult {
    data class Success(val dto: SettingsDto) : GetSettingsResult()
    data class Failure(val error: Throwable) : GetSettingsResult()
}