package com.example.ejercicio_indiv_1_m_6

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName= "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val idTask: Int = 0,
    val alias: String,
    val name: String,
    val age: Int
)
