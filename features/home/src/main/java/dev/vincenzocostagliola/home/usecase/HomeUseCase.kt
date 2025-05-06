package dev.vincenzocostagliola.home.usecase

import dev.vincenzocostagliola.home.data.repository.Repository
import javax.inject.Inject

internal interface HomeUseCase {
}

internal class HomeUseCaseImpl @Inject internal constructor(
    private val repository: Repository
) : HomeUseCase {

}