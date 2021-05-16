package com.ptit.appsql.student

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ptit.appsql.AppDispatchers
import com.ptit.appsql.database.AppDatabase
import com.ptit.appsql.database.StudentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentViewModel(
    application: Application
): AndroidViewModel(application) {
    private val studentDao: StudentDao = AppDatabase.buildDatabase(application).studentDao()

    private val dispatchers = AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default)

    private val _students = MediatorLiveData<List<Student>>()
    val students: LiveData<List<Student>> get() = _students
    private var studentSource: LiveData<List<Student>> = MutableLiveData()

    lateinit var singleStudent: LiveData<Student>

    fun saveStudent(student: Student){
        viewModelScope.launch(dispatchers.io){
            studentDao.insertStudent(student)
        }
    }

    fun getStudent() {
        viewModelScope.launch(dispatchers.main) {
            _students.removeSource(studentSource)
            withContext(dispatchers.io) {
                studentSource = studentDao.getStudentList()
            }
            _students.addSource(studentSource){
                _students.value = it
                Log.d("Student", it.toString())
            }
        }
    }

    fun getStudentById(id: Long) {
        singleStudent = studentDao.getStudentById(id)
    }

    fun updateStudent(student: Student){
        viewModelScope.launch(dispatchers.io){
            studentDao.updateStudent(student)
        }
    }

    fun searchStudent(name: String){
        viewModelScope.launch(dispatchers.main) {
            _students.removeSource(studentSource)
            withContext(dispatchers.io) {
                studentSource = studentDao.searchStudentByName("%$name%")
            }
            _students.addSource(studentSource){
                _students.value = it
                Log.d("Student", it.toString())
            }
        }
    }

    fun deleteStudent(student: Student){
        viewModelScope.launch(dispatchers.io) {
            studentDao.deleteStudent(student)
        }
    }
}