package dev.vincenzocostagliola.settings.data.dto.result

import dev.vincenzocostagliola.settings.data.dto.SettingsDto

internal sealed class GetSettingsDtoResult {
    data class Success(val dto: SettingsDto?) : GetSettingsDtoResult()
    data class Failure(val error: Throwable) : GetSettingsDtoResult()
}