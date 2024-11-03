package com.example.rickandmortylab

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class RickAndMortyApiClient(private val client: HttpClient) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getCharacters(): List<CharacterDTO> {
        return try {
            val response: HttpResponse = client.get("https://rickandmortyapi.com/api/character")
            if (response.status == HttpStatusCode.OK) {
                response.body<ResponseWrapper<CharacterDTO>>().results
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getLocations(): List<LocationDTO> {
        return try {
            val response: HttpResponse = client.get("https://rickandmortyapi.com/api/location")
            if (response.status == HttpStatusCode.OK) {
                response.body<ResponseWrapper<LocationDTO>>().results
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

@Serializable
data class ResponseWrapper<T>(
    val results: List<T>
)

@Serializable
data class CharacterDTO(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

@Serializable
data class LocationDTO(
    val id: Int,
    val name: String,
    val type: String?,
    val dimension: String?
)
