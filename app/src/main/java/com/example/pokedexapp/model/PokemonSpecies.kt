package com.example.pokedexapp.model

data class PokemonSpecies(
    val egg_groups: List<NamedAPIResource>? = emptyList(),
    val color: NamedAPIResource,
    val habitat: NamedAPIResource? = null
)
