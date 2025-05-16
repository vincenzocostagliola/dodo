package dev.vincenzocostagliola.settings.data.domain.result

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.settings.data.domain.SettingsDomain
import dev.vincenzocostagliola.settings.data.dto.SettingsDto

internal sealed class GetSettingsResult {
    data class Success(val dto: SettingsDomain?) : GetSettingsResult()
    data class Failure(val error: AppError) : GetSettingsResult()
}