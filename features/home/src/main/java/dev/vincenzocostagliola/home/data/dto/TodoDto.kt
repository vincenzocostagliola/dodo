package dev.vincenzocostagliola.home.data.dto

import dev.vincenzocostagliola.home.data.domain.Todo

internal data class TodoDto(
    val id : Int,
    val title : String,
    val description : String,
    val status : String
){
    fun toDomain() : Todo {
        return Todo(
            id = id,
            title = title,
            description = description,
            status = status
        )
    }
}
