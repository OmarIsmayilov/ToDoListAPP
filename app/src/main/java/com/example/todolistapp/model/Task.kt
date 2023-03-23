package com.example.todolistapp.model


data class Task(
    var description: String = "",
    val priority: Boolean? = false,
    val time: String = "",
    val title: String = "",
    var visibility: Boolean  = false,
    val complete : Boolean = false,
    var id : String = ""
)