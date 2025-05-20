package com.lemieux.tasks.domain.model

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows

class TaskTest {
    @Test
    fun `Task creation with valid name`() {
        val task = Task(name = "Test Task")
        assertEquals("Test Task", task.name)
    }

    @Test
    fun `Task creation with empty name throws exception`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Task(name = "")
        }
        assertEquals("Task name cannot be empty", exception.message)
    }

    @Test
    fun `Task creation with null name throws exception`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Task(name = "")
        }
        assertEquals("Task name cannot be empty", exception.message)
    }
}
