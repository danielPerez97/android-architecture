package dev.danperez.petlist.screens.petlist

data class PetListUiState(
    val pets: List<Pet> = emptyList(),
    val selectedPet: Pet? = null,
    val counter: Int = 0,
)