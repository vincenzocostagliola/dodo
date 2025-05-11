package dev.vincenzocostagliola.home.repository

import dev.vincenzocostagliola.data.error.AppError
import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.ActivitiesDao
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.home.data.dto.result.GetActivityResultDto
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.threeten.bp.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    lateinit var errorManagement: ErrorManagement
    private lateinit var repository: RepositoryImpl
    private lateinit var db: DodoDB
    private lateinit var dao: ActivitiesDao
    private lateinit var error: AppError



    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dao = mockk(relaxed = true)
        db = mockk {
            every { activitiesDao() } returns dao
        }
        error = mockk<AppError.ErrorGenericCause>()

        repository = RepositoryImpl( db)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
        testScope.cancel()
    }

    @Test
    fun `getAllActivities returns Success when DAO returns data`() = testScope.runTest {
        val dbList = listOf(
            TodoDb(
                id = 1,
                title = "Test",
                description = "Description",
                status = "OPEN",
                addedDate = OffsetDateTime.now().toString()
            )
        )

        coEvery { dao.getAllActivities() } returns dbList

        val result = repository.getAllActivities().first()

        assert(result is GetActivityResultDto.Success)
        val success = result as GetActivityResultDto.Success
        assertEquals(1, success.list.size)
        assertEquals("Test", success.list[0].title)
    }

    @Test
    fun `getAllActivities returns Failure when exception occurs`() = testScope.runTest {
        val exception = Throwable()

        coEvery { dao.getAllActivities() } throws exception

        val result = repository.getAllActivities().first()

        assert(result is GetActivityResultDto.Failure)
        val failure = result as GetActivityResultDto.Failure
        assertEquals(exception, failure.error)
    }
}
