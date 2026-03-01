package com.example.serviciosmartins.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RequestDao {

    @Query("SELECT * FROM requests ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<RequestEntity>>

    @Query("SELECT * FROM requests WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): RequestEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: RequestEntity)

    @Update
    suspend fun update(entity: RequestEntity)

    @Delete
    suspend fun delete(entity: RequestEntity)
}
