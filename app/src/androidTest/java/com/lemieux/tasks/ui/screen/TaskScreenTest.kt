package com.lemieux.tasks.ui.screen

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lemieux.tasks.MainActivity
import com.lemieux.tasks.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskScreenTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        // Launch the activity
        activityScenarioRule.scenario.onActivity { activity ->
            // Ensure the activity is fully started
        }
    }

    @Test
    fun testAddTask() {
        // Click the add button
        onView(withId(R.id.add_task_button))
            .perform(click())

        // Type text in the input field
        onView(withId(R.id.task_input))
            .perform(typeText("Test Task"))

        // Click the add button again to save the task
        onView(withId(R.id.add_task_button))
            .perform(click())

        // Verify the task is displayed
        onView(withText("Test Task"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testDeleteTask() {
        // Add a test task first
        onView(withId(R.id.add_task_button))
            .perform(click())
        onView(withId(R.id.task_input))
            .perform(typeText("Task to Delete"))
        onView(withId(R.id.add_task_button))
            .perform(click())

        // Find and click the delete button for the task
        onView(withText("Task to Delete"))
            .perform(click())
        onView(withId(R.id.delete_button))
            .perform(click())

        // Verify the task is no longer displayed
        onView(withText("Task to Delete"))
            .check(matches(isNotDisplayed()))
    }

    @Test
    fun testEmptyTaskInput() {
        // Click the add button without entering text
        onView(withId(R.id.add_task_button))
            .perform(click())

        // Verify no empty task is added
        onView(withText(""))
            .check(matches(isNotDisplayed()))
    }
}
