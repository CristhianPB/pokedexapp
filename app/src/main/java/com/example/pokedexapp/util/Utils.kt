package com.example.pokedexapp.util

fun extractPokemonId(url: String): Int {
    return url.trimEnd('/').split("/").last().toInt()
}
