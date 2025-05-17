package dev.vincenzocostagliola.home.data.dto.result

import dev.vincenzocostagliola.home.data.dto.SettingsDto

internal sealed class GetSettingsDtoResult {
    data class Success(val dto: SettingsDto?) : GetSettingsDtoResult()
    data class Failure(val error: Throwable) : GetSettingsDtoResult()
}