package dev.vincenzocostagliola.home.data.domain.result

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.home.data.domain.SettingsDomain

internal sealed class GetSettingsResult {
    data class Success(val settings: SettingsDomain?) : GetSettingsResult()
    data class Failure(val error: AppError) : GetSettingsResult()
}