package dev.vincenzocostagliola.home.data.domain

data class Todo(
    val id : Int,
    val title : String,
    val description : String,
    val status : String
)