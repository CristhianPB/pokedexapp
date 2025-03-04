package com.example.pokedexapp.repository

import com.example.pokedexapp.model.Pokemon
import com.example.pokedexapp.network.PokemonApi

class PokemonRepository(private val api: PokemonApi) {
    suspend fun getPokemonList(limit: Int): List<Pokemon> {
        return api.getPokemonList(limit).results
    }
}
