package com.ptit.appsql.order

import android.app.Application
import androidx.lifecycle.*
import com.ptit.appsql.AppDispatchers
import com.ptit.appsql.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(
    application: Application
): AndroidViewModel(application)  {
    private val dispatchers = AppDispatchers(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default)
    private val orderDao = AppDatabase.buildDatabase(application).orderDao()

    private val _orders = MediatorLiveData<List<Order>>()
    val order: LiveData<List<Order>> get() = _orders
    private var orderSource: LiveData<List<Order>> = MutableLiveData()

    lateinit var singleOrder: LiveData<Order>

    fun getAllOrder(){
        viewModelScope.launch(dispatchers.main){
            _orders.removeSource(orderSource)
            withContext(dispatchers.io){
                orderSource = orderDao.getAllOrder()
            }
            _orders.addSource(orderSource){
                _orders.value = it
            }
        }
    }

    fun getOrderById(orderId: Long){
        singleOrder = orderDao.getOrderById(orderId)
    }

    fun addOrder(order: Order){
        viewModelScope.launch(dispatchers.io){
            orderDao.insertOrder(order)
        }
    }

    fun updateOrder(order: Order){
        viewModelScope.launch(dispatchers.io){
            orderDao.updateOrder(order)
        }
    }

    fun deleteOrder(order: Order){
        viewModelScope.launch(dispatchers.io){
            orderDao.deleteOrder(order)
        }
    }

    fun searchOrder(name: String){
        viewModelScope.launch(dispatchers.main) {
            _orders.removeSource(orderSource)
            withContext(dispatchers.io) {
                orderSource = orderDao.searchOrderByName("%$name%")
            }
            _orders.addSource(orderSource){
                _orders.value = it
            }
        }
    }
}