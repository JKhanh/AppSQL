package com.ptit.appsql.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ptit.appsql.student.Student

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Query("SELECT * FROM student")
    fun getStudentList(): LiveData<List<Student>>

    @Query("SELECT * FROM student WHERE id =:id")
    fun getStudentById(id: Long): LiveData<Student>

    @Query("SELECT * FROM student WHERE name LIKE :name")
    fun searchStudentByName(name: String): LiveData<List<Student>>

    @Update
    fun updateStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)
}