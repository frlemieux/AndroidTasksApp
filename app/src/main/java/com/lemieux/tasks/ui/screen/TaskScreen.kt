package com.lemieux.tasks.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lemieux.tasks.ui.model.TaskUi
import com.lemieux.tasks.ui.theme.TasksAppTheme

@Composable
fun TaskManagerScreen(
    taskViewModel: TaskViewModel = hiltViewModel(),
    modifier: Modifier,
) {
    val taskList by taskViewModel.uiState.collectAsState()
    TaskList(
        taskUiState = taskList,
        deleteTask = taskViewModel::deleteTask,
        addTask = taskViewModel::addTask,
        onValueChange = taskViewModel::onValueChange,
        modifier = modifier,
    )
}

@Composable
fun TaskList(
    taskUiState: TaskUiState,
    deleteTask: (TaskUi) -> Unit,
    addTask: () -> Unit,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
            )
            .padding(start = 24.dp, end = 24.dp, top = 24.dp),
    ) {
        val taskUiStateSuccess = (taskUiState as TaskUiState.Success)
        Text(
            text = "Reminders",
            fontWeight = FontWeight.Bold,
            style = TextStyle(fontSize = 24.sp),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
        ) {
            TextField(
                value =
                    TextFieldValue(
                        text = taskUiStateSuccess.newTask,
                        selection = TextRange(taskUiStateSuccess.newTask.length),
                    ),
                onValueChange = { onValueChange(it.text) },
                label = { Text("Enter your task") },
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                maxLines = 1,
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            addTask()
                        },
                    ),
                modifier = Modifier
                    .weight(1f)
                    .testTag("task_input")
                    .focusRequester(focusRequester),
            )
            TextButton(
                onClick = { addTask() },
                modifier =
                    Modifier
                        .testTag("add_task_tag")
                        .wrapContentWidth()
                        .padding(horizontal = 8.dp)
                        .background(MaterialTheme.colorScheme.onPrimary),
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "New Task",
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Add",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("task_list")
        ) {
            items(
                items = taskUiState.tasks,
                key = { it.hashCode() },
            ) { task ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .testTag("task_item")
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onSecondary)
                        .border(border = ButtonDefaults.outlinedButtonBorder)
                        .clip(shape = RoundedCornerShape(6.dp)),
                ) {
                    Text(text = "- ${task.name}", modifier = Modifier.padding(16.dp))
                    IconButton(
                        onClick = { deleteTask(task) },
                        modifier = Modifier.testTag("delete_task_button")
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Task",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
    TasksAppTheme {
        TaskList(
            taskUiState = TaskScreenPreviewData.PreviewTaskUiState,
            deleteTask = {},
            addTask = {},
            onValueChange = {},
        )
    }
}
