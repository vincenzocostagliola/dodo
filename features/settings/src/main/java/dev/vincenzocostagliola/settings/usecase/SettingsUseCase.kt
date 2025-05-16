package dev.vincenzocostagliola.settings.usecase

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.settings.repository.Repository

internal class SettingsUseCase(
    val repository: Repository,
    val errorManagement: ErrorManagement
) {
}