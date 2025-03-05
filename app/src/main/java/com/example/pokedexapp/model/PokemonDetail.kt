// PokemonDetail.kt
package com.example.pokedexapp.model

data class NamedAPIResource(
    val name: String,
    val url: String
)

data class PokemonAbility(
    val ability: NamedAPIResource,
    val is_hidden: Boolean,
    val slot: Int
)

data class PokemonStat(
    val base_stat: Int,
    val effort: Int,
    val stat: NamedAPIResource
)

data class PokemonPokeathlonStat(
    val name: String,
    val max_stat: Int,
    val color: String
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: NamedAPIResource
)

data class Sprites(
    val other: OtherSprites
)

data class OtherSprites(
    val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    val imageUrl: String?
)

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbility> = emptyList(),
    val stats: List<PokemonStat> = emptyList(),
    val pokeathlon_stats: List<PokemonPokeathlonStat> = emptyList(),
    val types: List<PokemonTypeSlot> = emptyList(),
    val forms: List<NamedAPIResource> = emptyList(),
    val location_area_encounters: String = "",
    val sprites: Sprites,
    val species: NamedAPIResource
)
