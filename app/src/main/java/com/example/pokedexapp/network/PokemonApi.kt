package com.example.pokedexapp.network

import com.example.pokedexapp.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokemonResponse
}
