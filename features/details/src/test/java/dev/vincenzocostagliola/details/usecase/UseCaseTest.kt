package dev.vincenzocostagliola.details.usecase

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.details.MainDispatcherRule
import dev.vincenzocostagliola.details.data.domain.Todo
import dev.vincenzocostagliola.details.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.details.data.dto.result.GetActivityResultDto
import dev.vincenzocostagliola.details.repository.Repository
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class UseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    internal lateinit var repository: Repository

    @MockK
    lateinit var errorManagement: ErrorManagement
    private lateinit var useCase: UseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UseCaseImpl(repository, errorManagement)
    }

    @Test
    fun `getTodo emits Success when repository returns Success DTO`() = runTest {
        val todo = Todo(1, "Title", "Desc", Todo.TodoStatus.STARTED, OffsetDateTime.now())
        val dto = GetActivityResultDto.Success(todo.toDto(todo.addedDate))

        coEvery { repository.getTodo(1) } returns flowOf(dto)

        val result = useCase.getTodo(1).first()
        assertTrue(result is GetActivityResult.Success)
        assertEquals("Title", (result as GetActivityResult.Success).todo.title)
    }

    @Test
    fun `getTodo emits NotFound when repository returns NotFound DTO`() = runTest {
        coEvery { repository.getTodo(1) } returns flowOf(GetActivityResultDto.NotFound)

        val result = useCase.getTodo(1).first()
        assertTrue(result is GetActivityResult.NotFound)
    }

    @Test
    fun `saveTodo calls repository with converted DTO`() = runTest {
        val todo = Todo(1, "Title", "Desc", Todo.TodoStatus.TOSTART, OffsetDateTime.now())
        coEvery { repository.saveTodo(any()) } just Runs

        useCase.saveTodo(todo)

        coVerify {
            repository.saveTodo(withArg { dto ->
                assertEquals("Title", dto.title)
                assertEquals("Desc", dto.description)
                assertEquals("TOSTART", dto.status)
            })
        }
    }


}
