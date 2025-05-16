package dev.vincenzocostagliola.settings.ui

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.designsystem.composables.Option
import dev.vincenzocostagliola.settings.data.domain.SettingsDomain
import dev.vincenzocostagliola.settings.data.domain.result.GetSettingsResult
import dev.vincenzocostagliola.settings.usecase.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

internal sealed class SettingsScreenState {
    data object Loading : SettingsScreenState()
    data class Success(val list: List<Option>) : SettingsScreenState()
    data class Error(val error: AppError) : SettingsScreenState()
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


    private val _settingScreenState: MutableStateFlow<SettingsScreenState> =
        MutableStateFlow(SettingsScreenState.Loading)
    internal val settingScreenState: StateFlow<SettingsScreenState>
        get() = _settingScreenState

    init {
        sendEvent(SettingsScreenEvents.GetSettings)
    }

    fun sendEvent(event: SettingsScreenEvents) {
        Timber.d("SettingScreen - SettingVieModel - sendEvent: $event")

        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is SettingsScreenEvents.GetSettings -> getSavedSettings()
                is SettingsScreenEvents.PerformDialogAction -> TODO()
                is SettingsScreenEvents.SaveSettings -> TODO()
            }
        }
    }

    private suspend fun getSavedSettings() {
        Timber.d("SettingScreen - SettingVieModel: getSavedSettings")

        with(Dispatchers.Main) {
            _settingScreenState.update {
                SettingsScreenState.Loading
            }
        }
        val result = useCase.getSettings()
        Timber.d("SettingScreen - SettingVieModel: getSavedSettings - resul: $result")
        with(Dispatchers.Main) {

            when (result) {
                is GetSettingsResult.Failure -> {
                    _settingScreenState.update {
                        SettingsScreenState.Error(result.error)
                    }
                }

                is GetSettingsResult.Success -> {
                    _settingScreenState.update {
                        SettingsScreenState.Success(result.settings.toOptionList())
                    }
                }
            }
        }
    }

    private fun SettingsDomain?.toOptionList(): List<Option> {
        return this?.let {
            it.possibleSelections.map {
                Option(
                    value = it.name,
                    selection = it.name,
                    isSelected = it == this.orderSelected
                )
            }
        } ?: run {
            SettingsDomain.OrderBy.entries.map {
                Option(
                    value = it.name,
                    selection = it.name,
                    isSelected = false
                )
            }
        }
    }
}