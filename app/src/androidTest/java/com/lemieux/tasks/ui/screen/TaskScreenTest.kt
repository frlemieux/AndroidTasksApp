package com.lemieux.tasks.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lemieux.tasks.MainActivity
import com.lemieux.tasks.ui.model.TaskUi
import kotlinx.collections.immutable.toPersistentList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalComposeUiApi::class)
@RunWith(AndroidJUnit4::class)
class TaskScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun taskList_addAndDeleteTask_updatesCorrectly() {
        val taskList = mutableStateListOf<TaskUi>(TaskUi("Initial Task"))
        var newTaskText = ""

        composeTestRule.setContent {
            TaskList(
                taskUiState = TaskUiState.Success(
                    tasks = taskList.toPersistentList(),
                    newTask = newTaskText
                ),
                deleteTask = { taskList.remove(it) },
                addTask = {
                    taskList.add(TaskUi(newTaskText))
                },
                onValueChange = { newTaskText = it }
            )
        }

        val newTask = "Buy groceries"

        composeTestRule
            .onNodeWithTag("task_input")
            .performTextInput(newTask)

        composeTestRule
            .onNodeWithTag("add_task_tag")
            .performClick()

        composeTestRule
            .waitUntil(timeoutMillis = 3_000) {
                taskList.any { item -> item.name == newTask }
            }

        // Ensure the task is visible
        composeTestRule
            .onNodeWithText("- $newTask")
            .assertIsDisplayed()

        // Delete the initial task
        composeTestRule
            .onAllNodesWithTag("delete_task_button")
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 3_000) {
            !taskList.any { it.name == "Initial Task" }
        }

        // Ensure the old task is gone
        composeTestRule
            .onNodeWithText("- Initial Task")
            .assertDoesNotExist()
    }
}