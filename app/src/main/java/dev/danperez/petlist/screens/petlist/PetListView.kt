package dev.danperez.petlist.screens.petlist

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView

class PetListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
): AbstractComposeView(context, attrs, defStyle)
{
    private var state by mutableStateOf(PetListUiState())

    @Composable
    override fun Content() {
        Text(
            text = "Hello $state!",
            modifier = Modifier.fillMaxSize(),
        )
    }

    fun render(newState: PetListUiState) {
        state = newState
    }
}