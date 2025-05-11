package dev.vincenzocostagliola.home.ui

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.home.createTodoDtoList
import dev.vincenzocostagliola.home.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.home.usecase.HomeUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeViewModelTest {

    @MockK
    lateinit var homeUseCase: HomeUseCase

    private lateinit var vm: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cancel()
        clearAllMocks()
    }

    private fun createViewModel() {
        vm = HomeViewModel(homeUseCase)
    }

    @Test
    fun `getAllActivities returns Success state when data is returned with emptyList`() =
        testScope.runTest {
            every { homeUseCase.getAllActivities() } returns flow {
                emit(GetActivityResult.Success(emptyList()))
            }

            createViewModel()
            vm.sendEvent(HomeScreenEvents.GetAllActivities)

            val result = vm.homeScreenState
                .filterIsInstance<HomeScreenState.Success>()
                .first()

            assertEquals(HomeScreenState.Success(emptyList()), result)
        }

    @Test
    fun `getAllActivities returns Success state when data is returned with filledList`() =
        testScope.runTest {
            val dtoList = createTodoDtoList()

            every { homeUseCase.getAllActivities() } returns flow {

                emit(GetActivityResult.Success(dtoList.map { it.toDomain() }))
            }

            createViewModel()
            vm.sendEvent(HomeScreenEvents.GetAllActivities)

            val result = vm.homeScreenState
                .filterIsInstance<HomeScreenState.Success>()
                .first()

            assertEquals(
                HomeScreenState.Success(
                    dtoList
                        .map { it.toDomain() }
                        .map { it.toInfoUi() }
                ), result)
        }

    @Test
    fun `getAllActivities returns Error state when an error occurs`() = testScope.runTest {

        val error = AppError.GenericError
        every { homeUseCase.getAllActivities() } returns flow {
            emit(GetActivityResult.Failure(error))
        }

        createViewModel()
        vm.sendEvent(HomeScreenEvents.GetAllActivities)

        val result = vm.homeScreenState
            .filterIsInstance<HomeScreenState.Error>()
            .first()

        assertEquals(HomeScreenState.Error(error), result)
    }

    @Test
    fun `getAllActivities returns Loading then Success`() = testScope.runTest {
        every { homeUseCase.getAllActivities() } returns flow {
            emit(GetActivityResult.Success(emptyList()))
        }

        createViewModel()
        vm.sendEvent(HomeScreenEvents.GetAllActivities)

        val results = vm.homeScreenState
            .take(2)
            .toList()

        assertEquals(HomeScreenState.Loading, results[0])
        assertEquals(HomeScreenState.Success(emptyList()), results[1])
    }
}
