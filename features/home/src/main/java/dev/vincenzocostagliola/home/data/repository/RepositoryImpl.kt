package dev.vincenzocostagliola.home.data.repository

import dev.vincenzocostagliola.data.error.ErrorManagement
import dev.vincenzocostagliola.db.TodoDb
import dev.vincenzocostagliola.db.DodoDB
import dev.vincenzocostagliola.home.data.dto.TodoDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import org.threeten.bp.OffsetDateTime
import timber.log.Timber

internal interface Repository {
    suspend fun getAllActivities(): Flow<List<TodoDto>>
}

internal class RepositoryImpl(
    private val errorManagement: ErrorManagement,
    private val db: DodoDB
) : Repository {

    override suspend fun getAllActivities(): Flow<List<TodoDto>> {
        return flow {
            val firstList = db.activitiesDao().getAllActivities()
            Timber.d("HomeScreen - Repository -  getAllActivities - dbList: $firstList")

            if(firstList.size < 2) {
                createFakeList().forEach {
                    db.activitiesDao().insertTodo(it.toTodoDb())
                    Timber.d("HomeScreen - Repository -  Insert to DB - $it")
                }
            }
            val list: List<TodoDto> = db.activitiesDao().getAllActivities().toDto()
            Timber.d("HomeScreen - Repository -  getAllActivities - dbList: $list")


            emit(list)
        }
    }

    //TODO to remove is just for test
    private fun createFakeList(): List<TodoDto> {
            val list: List<TodoDto> = listOf(
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean",
                    addedDate = OffsetDateTime.now()
                ),
                TodoDto(
                    id = 9092,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean",
                    addedDate = OffsetDateTime.now().plusDays(1)
                ),
                TodoDto(
                    id = 9093,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean",
                    addedDate = OffsetDateTime.now().plusDays(2)
                ),
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean",
                    addedDate = OffsetDateTime.now().plusDays(2)
                ),
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean",
                    addedDate = OffsetDateTime.now().plusDays(3)
                ),
                TodoDto(
                    id = 9091,
                    title = "consectetuer",
                    description = "cubilia",
                    status = "aenean",
                    addedDate = OffsetDateTime.now().plusDays(4)
                ),
            )
            Timber.d("HomeScreen - Repository -  getAllActivities  - fakeList: $list")
        return list
    }

    private fun List<TodoDb>.toDto(): List<TodoDto> {
        return this.map { activity ->
            with(activity) {
                TodoDto(
                    id = id,
                    title = title,
                    description = description,
                    status = status,
                    addedDate = OffsetDateTime.parse(addedDate)
                )
            }
        }
    }
}