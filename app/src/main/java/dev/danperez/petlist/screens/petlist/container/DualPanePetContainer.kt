package dev.danperez.petlist.screens.petlist.container

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import dev.danperez.petlist.screens.petlist.PetDetailView
import dev.danperez.petlist.screens.petlist.PetListUiState
import dev.danperez.petlist.screens.petlist.PetListView

class DualPanePetContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
): LinearLayout(context, attrs, defStyle), PetContainer
{
    private lateinit var petListView: PetListView
    private lateinit var petDetailView: PetDetailView

    override fun onFinishInflate() {
        super.onFinishInflate()
        petListView = getChildAt(0) as PetListView
        petDetailView = getChildAt(1) as PetDetailView
    }

    override fun render(uiState: PetListUiState) {
        petListView.render(uiState)
        petDetailView.render(uiState)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}