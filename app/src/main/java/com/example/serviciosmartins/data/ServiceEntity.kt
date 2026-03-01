package com.example.serviciosmartins.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val description: String,
    val phone: String,
    val createdAt: Long = System.currentTimeMillis()
)
