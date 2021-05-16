package com.ptit.appsql.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ptit.appsql.order.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: Order)

    @Update
    fun updateOrder(order: Order)

    @Delete
    fun deleteOrder(order: Order)

    @Query("SELECT * FROM `order`")
    fun getAllOrder(): LiveData<List<Order>>

    @Query("SELECT * FROM `order` WHERE itemName =:itemName")
    fun searchOrderByName(itemName: String): LiveData<List<Order>>

    @Query("SELECT * FROM `order` WHERE id =:id")
    fun getOrderById(id: Long): LiveData<Order>
}