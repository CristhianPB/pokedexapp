package com.example.pokedexapp.model

data class Pokemon(
    val name: String,
    val url: String
)

data class PokemonResponse(
    val results: List<Pokemon>
)