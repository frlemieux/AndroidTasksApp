package com.lemieux.tasks.data.repository

import com.lemieux.tasks.data.local.TaskEntity
import com.lemieux.tasks.data.local.TaskDao
import com.lemieux.tasks.domain.model.Task
import com.lemieux.tasks.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals
import kotlin.reflect.KClass
import kotlin.reflect.full.memberExtensionFunctions
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.KFunction

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryImplTest {
    private lateinit var taskDao: TaskDao
    private lateinit var repository: TaskRepository
    private lateinit var testScope: TestScope
    private lateinit var dispatcher: TestDispatcher

    @Before
    fun setUp() {
        taskDao = mock()
        repository = TaskRepositoryImpl(taskDao)
        dispatcher = UnconfinedTestDispatcher()
        testScope = TestScope(dispatcher)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllTasks returns empty list when no tasks exist`() = testScope.runTest {
        val taskEntities = flowOf(emptyList<TaskEntity>())
        whenever(taskDao.getAllTasks()).thenReturn(taskEntities)
        
        val result = repository.getAllTasks()
            .flowOn(dispatcher)
            .first()
        assertEquals(emptyList<Task>(), result)
    }

    @Test
    fun `getAllTasks returns list of tasks when tasks exist`() = testScope.runTest {
        val taskEntity = TaskEntity(taskName = "Test Task")
        val task = Task(name = "Test Task")
        
        val taskEntities = flowOf(listOf(taskEntity))
        whenever(taskDao.getAllTasks()).thenReturn(taskEntities)
        
        val result = repository.getAllTasks()
            .flowOn(dispatcher)
            .first()
        assertEquals(listOf(task), result)
    }

    @Test
    fun `insertTask inserts task correctly`() = testScope.runTest {
        val task = Task(name = "Test Task")
        
        repository.insertTask(task)
        
        verify(taskDao).insert(task.toEntity())
    }

    @Test
    fun `deleteTask deletes task correctly`() = testScope.runTest {
        val task = Task(name = "Test Task")
        
        repository.deleteTask(task)
        
        verify(taskDao).delete(task.name)
    }
}
