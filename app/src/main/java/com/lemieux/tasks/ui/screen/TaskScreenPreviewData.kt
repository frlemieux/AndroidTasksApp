package com.lemieux.tasks.ui.screen

import com.lemieux.tasks.ui.model.TaskUi
import kotlinx.collections.immutable.persistentListOf

object TaskScreenPreviewData {
    val PreviewTasks = persistentListOf<TaskUi>(
        TaskUi(name = "Buy groceries"),
        TaskUi(name = "Do laundry"),
        TaskUi(name = "Call mom"),
    )

    val PreviewTaskUiState = TaskUiState.Success(
        newTask = "",
        tasks = PreviewTasks
    )
}
