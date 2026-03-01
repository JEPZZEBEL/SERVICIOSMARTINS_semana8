package com.example.serviciosmartins.network

import retrofit2.http.GET

interface ApiService {
    @GET("jokes/random")
    suspend fun randomJoke(): ChuckJokeDto
}