package com.ptit.appsql.student

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    val name: String,
    val gender: Boolean,
    val mark: Float
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
