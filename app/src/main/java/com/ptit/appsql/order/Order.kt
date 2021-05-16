package com.ptit.appsql.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(
    val itemName: String,
    val dateOrder: String,
    val price: Float,
    val ratingOrder: Int
){
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
