package dev.danperez.petlist.screens.petlist.container

import dev.danperez.petlist.screens.petlist.PetListUiState

interface PetContainer
{
    fun render(uiState: PetListUiState)
    fun onBackPressed(): Boolean
}