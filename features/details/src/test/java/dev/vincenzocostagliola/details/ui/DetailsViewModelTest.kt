package dev.vincenzocostagliola.details.ui

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.DialogAction
import dev.vincenzocostagliola.designsystem.composables.FieldForm
import dev.vincenzocostagliola.details.data.domain.Todo
import dev.vincenzocostagliola.details.data.domain.result.GetActivityResult
import dev.vincenzocostagliola.details.usecase.UseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.OffsetDateTime

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase: UseCase = mockk(relaxed = true)
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        viewModel = DetailsViewModel(useCase)
    }

    @Test
    fun `getTodo emits Success when use case returns valid result`() = runTest {
        val todo = Todo(id = 1, title = "title", description = "desc", status = Todo.TodoStatus.STARTED, addedDate = OffsetDateTime.now())
        val result = GetActivityResult.Success(todo)
        coEvery { useCase.getTodo(1) } returns flowOf(result)

        viewModel.sendEvent(ScreenEvents.GetTodo(1))

        val state = viewModel.screenState.first { it is ScreenState.Success }
        state as ScreenState.Success
        assertEquals("title", (state.todo.list.first { it is FieldForm.Title } as FieldForm.Title).text)
    }

    @Test
    fun `getTodo with null ID emits Success with empty info`() = runTest {
        viewModel.sendEvent(ScreenEvents.GetTodo(null))

        val state = viewModel.screenState.first { it is ScreenState.Success }
        state as ScreenState.Success

        val title = state.todo.list.first { it is FieldForm.Title } as FieldForm.Title
        assertTrue(title.text.isEmpty())
        assertFalse(state.todo.readOnly)
    }

    @Test
    fun `getTodo emits Error on failure`() = runTest {
        coEvery { useCase.getTodo(1) } returns flowOf(GetActivityResult.Failure(AppError.GenericError))

        viewModel.sendEvent(ScreenEvents.GetTodo(1))

        val state = viewModel.screenState.first { it is ScreenState.Error }
        state as ScreenState.Error
        assertTrue(state.error is AppError.GenericError)
    }

    @Test
    fun `retry performs getTodo again`() = runTest {
        val todo = Todo(1, "RetryTitle", "RetryDesc", Todo.TodoStatus.FINISHED, OffsetDateTime.now())
        val result = GetActivityResult.Success(todo)
        coEvery { useCase.getTodo(1) } returns flowOf(result)

        viewModel.sendEvent(ScreenEvents.GetTodo(1))
        viewModel.sendEvent(ScreenEvents.PerformDialogAction(DialogAction.Retry))

        val state = viewModel.screenState.first { it is ScreenState.Success }
        state as ScreenState.Success
        assertEquals("RetryTitle", (state.todo.list.first { it is FieldForm.Title } as FieldForm.Title).text)
    }

    @Test
    fun `onValueChanged updates form title`() = runTest {
        val newTitle = FieldForm.Title("Updated", singleLine = true, isError = false)
        viewModel.sendEvent(ScreenEvents.OnValueChanged(newTitle))

        val state = viewModel.screenState.first { it is ScreenState.Success }
        state as ScreenState.Success
        val title = state.todo.list.first { it is FieldForm.Title } as FieldForm.Title
        assertEquals("Updated", title.text)
    }


}
