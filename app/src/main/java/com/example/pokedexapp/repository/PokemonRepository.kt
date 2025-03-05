package com.example.pokedexapp.repository

import com.example.pokedexapp.model.Pokemon
import com.example.pokedexapp.model.PokemonDetail
import com.example.pokedexapp.model.PokemonFullDetail
import com.example.pokedexapp.network.PokemonApi

class PokemonRepository(private val api: PokemonApi) {

    suspend fun getPokemonList(limit: Int): List<Pokemon> {
        return api.getPokemonList(limit).results
    }

    suspend fun getPokemonFullDetail(id: Int): PokemonFullDetail {
        val detail = api.getPokemonDetail(id)
        val species = api.getPokemonSpecies(id)
        return PokemonFullDetail(detail, species)
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetail {
        return api.getPokemonDetail(id)
    }
}
