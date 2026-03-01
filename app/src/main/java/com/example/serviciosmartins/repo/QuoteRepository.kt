package com.example.serviciosmartins.repo

import com.example.serviciosmartins.network.RetrofitClient
import timber.log.Timber

class QuoteRepository {

    // ✅ ahora devuelve solo el texto (String)
    suspend fun fetchRandomQuote(): Result<String> {
        return runCatching {
            val dto = RetrofitClient.api.randomJoke()
            Timber.i("Joke OK")
            dto.value
        }.onFailure { e ->
            Timber.e(e, "Joke ERROR")
        }
    }
}