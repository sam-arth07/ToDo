package com.example.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todo.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    //GET
    @Query("select * from todo_table order by id asc")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("select * from todo_table where id=:taskId")
    fun getSelectedTask(taskId:Int) : Flow<ToDoTask>

    //POST
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(addTask: ToDoTask)

    //UPDATE
    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    //DELETE
    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("delete from todo_table")
    suspend fun deleteAllTasks()

    //SEARCH DB
    @Query("select * from todo_table where title like :searchQuery or description like :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    //Sort by Low and High Priority
    @Query("select * from todo_table order by case when priority like 'L%' then 1 when priority like 'M%' then 2 when priority like 'H%' then 3 end")
    fun sortByLowPriority() : Flow<List<ToDoTask>>

    @Query("select * from todo_table order by case when priority like 'H%' then 1 when priority like 'M%' then 2 when priority like 'L%' then 3 end")
    fun sortByHighPriority() : Flow<List<ToDoTask>>
}