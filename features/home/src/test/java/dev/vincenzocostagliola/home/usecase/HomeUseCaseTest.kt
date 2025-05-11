package dev.vincenzocostagliola.home.usecase

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.home.createTodoDtoList
import dev.vincenzocostagliola.home.data.domain.Todo
import dev.vincenzocostagliola.home.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.home.data.dto.result.GetActivityResultDto
import dev.vincenzocostagliola.home.repository.Repository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class HomeUseCaseImplTest {

    @MockK
    lateinit var repository: Repository

    @MockK
    lateinit var errorManagement: ErrorManagement

    private lateinit var useCase: HomeUseCaseImpl

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        useCase = HomeUseCaseImpl(
            repository = repository,
            errorManagement = errorManagement
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
        testScope.cancel()
    }

    @Test
    fun `getAllActivities emits Success with mapped domain data`() = testScope.runTest {
        // Given
        val dtoList = createTodoDtoList()
        var domainModelList = dtoList.map { it.toDomain() }

        every { repository.getAllActivities() } returns flow {
            emit(GetActivityResultDto.Success(dtoList))
        }

        // When
        val result = useCase.getAllActivities().first()

        // Then
        assert(result is GetActivityResult.Success)
        assertEquals(domainModelList, (result as GetActivityResult.Success).list)
    }

    @Test
    fun `getAllActivities emits Success with empty list`() = testScope.runTest {
        // Given
        every { repository.getAllActivities() } returns flow {
            emit(GetActivityResultDto.Success(emptyList()))
        }

        // When
        val result = useCase.getAllActivities().first()

        // Then
        assert(result is GetActivityResult.Success)
        assertEquals(emptyList<Todo>(), (result as GetActivityResult.Success).list)
    }

    @Test
    fun `getAllActivities emits Failure when repository returns failure`() = testScope.runTest {
        // Given
        val error = Throwable()
        val appError = errorManagement.manageException(error)

        every { repository.getAllActivities() } returns flow {
            emit(GetActivityResultDto.Failure(error))
        }

        // When
        val result = useCase.getAllActivities().first()

        // Then
        assert(result is GetActivityResult.Failure)
        assertEquals(appError, (result as GetActivityResult.Failure).error)
    }
}
