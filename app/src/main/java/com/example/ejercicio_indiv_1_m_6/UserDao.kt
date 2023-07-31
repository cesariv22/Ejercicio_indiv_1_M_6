package com.example.ejercicio_indiv_1_m_6

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask (user: User)

    @Insert()
    suspend fun insertMultipleTask (list: List<User>)

    @Update
    suspend fun updateTask (user: User)

    @Delete
    fun deleteOneTask (user: User)

    @Query("DELETE FROM USER_TABLE")
    fun deleteAllTask()

    @Query("SELECT * FROM USER_TABLE")
    fun getAllTask1(): List<User>

    @Query("SELECT * FROM USER_TABLE ORDER BY idTask ASC")
    fun getAllTask(): List<User>

}