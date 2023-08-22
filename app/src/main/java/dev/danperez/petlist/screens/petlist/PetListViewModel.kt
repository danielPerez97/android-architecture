package dev.danperez.petlist.screens.petlist

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.danperez.petdb.PetDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class PetListViewModel @AssistedInject constructor(
    private val petDb: PetDb,
    @Assisted private val scope: CoroutineScope,
)
{
    private val viewModelState = MutableStateFlow(PetListUiState())

    fun uiState(): StateFlow<PetListUiState> = viewModelState

    init {
        Timber.i("init block of PetListViewModel")
        viewModelState.update {
            it.copy(
                pets = petDb.petQueries.selectAll().executeAsList().map { Pet(id = it._id, name = it.name) }
            )
        }

        scope.launch {
            withContext(Dispatchers.Default) {
                while(true) {
                    delay(1000)
                    viewModelState.update {
                        it.copy(counter = it.counter + 1)
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory
    {
        fun create(scope: CoroutineScope): PetListViewModel
    }
}