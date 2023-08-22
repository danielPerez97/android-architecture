package dev.danperez.petlist.screens.petlist

import android.content.Context
import android.util.AttributeSet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView

class PetDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
): AbstractComposeView(context, attrs, defStyle) {

    private var state by mutableStateOf(PetListUiState())

    @Composable
    override fun Content() {
        if (state.selectedPet != null) {
            Text(text = state.selectedPet!!.name)
        }
        else {
            Text("Count: ${state.counter}")
        }
    }

    fun render(newState: PetListUiState) {
        state = newState
    }

}