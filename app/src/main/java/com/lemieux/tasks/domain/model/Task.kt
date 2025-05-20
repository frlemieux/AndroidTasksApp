package com.lemieux.tasks.domain.model

data class Task(
    val name: String,
) {
    init {
        require(name.isNotBlank()) { "Task name cannot be empty" }
        require(name != null) { "Task name cannot be null" }
    }
}
