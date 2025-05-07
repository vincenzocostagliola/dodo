package dev.vincenzocostagliola.home.data.dto

import dev.vincenzocostagliola.home.data.domain.ActivityDomain

internal data class ActivityDto(
    val id : Int,
    val title : String,
    val description : String,
    val status : String
){
    fun toDomain() : ActivityDomain {
        return ActivityDomain(
            id = id,
            title = title,
            description = description,
            status = status
        )
    }
}
