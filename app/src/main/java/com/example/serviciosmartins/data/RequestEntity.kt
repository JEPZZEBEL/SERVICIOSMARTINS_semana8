package com.example.serviciosmartins.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "requests")
data class RequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clientName: String,
    val serviceType: String,
    val address: String,
    val phone: String,
    val notes: String,
    val createdAt: Long = System.currentTimeMillis()
)
