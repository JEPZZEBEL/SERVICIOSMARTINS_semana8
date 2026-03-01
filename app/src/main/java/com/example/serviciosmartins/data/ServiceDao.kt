package com.example.serviciosmartins.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Query("SELECT * FROM services ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ServiceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ServiceEntity): Long

    @Update
    suspend fun update(entity: ServiceEntity)

    @Delete
    suspend fun delete(entity: ServiceEntity)
}
