package dev.vincenzocostagliola.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.designsystem.composables.Option
import dev.vincenzocostagliola.settings.data.domain.SettingsDomain
import dev.vincenzocostagliola.settings.data.domain.SettingsDomain.Companion.toDomain
import dev.vincenzocostagliola.settings.data.domain.result.GetSettingsResult
import dev.vincenzocostagliola.settings.usecase.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

internal sealed class ScreenState {
    data object Loading : ScreenState()
    data class Success(val list: List<Option>) : ScreenState()
    data class Error(val error: AppError) : ScreenState()
}

sealed class SettingsScreenEvents {
    data object GetSettings : SettingsScreenEvents()
    data class PerformDialogAction(val dialogAction: DialogAction) : SettingsScreenEvents()
    data class SaveSettings(val selectedOption: Option) : SettingsScreenEvents()
}


@HiltViewModel
class SettingsScreenViewModel @Inject internal constructor(
    private val useCase: SettingsUseCase
) : ViewModel() {


    private val _settingScreenState: MutableStateFlow<ScreenState> =
        MutableStateFlow(ScreenState.Loading)
    internal val screenState: StateFlow<ScreenState>
        get() = _settingScreenState

    init {
        sendEvent(SettingsScreenEvents.GetSettings)
    }

    fun sendEvent(event: SettingsScreenEvents) {
        Timber.d("SettingScreen - SettingVieModel - sendEvent: $event")

        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is SettingsScreenEvents.GetSettings -> getSavedSettings()
                is SettingsScreenEvents.PerformDialogAction -> performDialogAction(event.dialogAction)
                is SettingsScreenEvents.SaveSettings -> saveSetting(event.selectedOption)
            }
        }
    }

    private suspend fun saveSetting(selectedOption: Option){
        Timber.d("SettingScreen - SettingVieModel - saveSetting: $selectedOption")

        useCase.saveSetting(selectedOption.toDomain())
    }

    private suspend fun performDialogAction(action: DialogAction) {
        when (action) {
            DialogAction.Leave -> Unit //TBD
            DialogAction.Quit -> {
                // Perform a logout if signed or go out from the app
                Unit
            }

            DialogAction.Retry -> sendEvent(SettingsScreenEvents.GetSettings)
        }
    }

    private suspend fun getSavedSettings() {
        Timber.d("SettingScreen - SettingVieModel: getSavedSettings")

        with(Dispatchers.Main) {
            _settingScreenState.update {
                ScreenState.Loading
            }
        }
        val result = useCase.getSettings()
        Timber.d("SettingScreen - SettingVieModel: getSavedSettings - resul: $result")
        with(Dispatchers.Main) {

            when (result) {
                is GetSettingsResult.Failure -> {
                    _settingScreenState.update {
                        ScreenState.Error(result.error)
                    }
                }

                is GetSettingsResult.Success -> {
                    _settingScreenState.update {
                        ScreenState.Success(result.settings.toOptionList())
                    }
                }
            }
        }
    }

    private fun SettingsDomain?.toOptionList(): List<Option> {
        return this?.let {
            SettingsDomain.OrderBy.entries.map {
                Option(
                    value = it.name,
                    isSelected = it == this.orderSelected
                )
            }
        } ?: run {
            SettingsDomain.OrderBy.entries.map {
                Option(
                    value = it.name,
                    isSelected = false
                )
            }
        }
    }
}