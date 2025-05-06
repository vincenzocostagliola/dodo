package dev.vincenzocostagliola.home.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.designsystem.composables.InfoUi
import dev.vincenzocostagliola.home.usecase.HomeUseCase
import javax.inject.Inject

internal sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Success(val list: List<InfoUi>) :
        HomeScreenState()

    data class Error(val error: AppError) : HomeScreenState()
}

sealed class HomeScreenEvents {
    data object GetCoinsData : HomeScreenEvents()
    data class PerformDialogAction(val dialogAction: DialogAction) : HomeScreenEvents()

}

@HiltViewModel
class HomeViewModel @Inject internal constructor(
    private val useCase: HomeUseCase
) : ViewModel() {
}