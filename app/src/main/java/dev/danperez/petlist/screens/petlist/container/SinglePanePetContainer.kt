package dev.danperez.petlist.screens.petlist.container

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import dev.danperez.petlist.screens.petlist.PetDetailView
import dev.danperez.petlist.screens.petlist.PetListUiState
import dev.danperez.petlist.screens.petlist.PetListView

class SinglePanePetContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
): FrameLayout(context, attrs, defStyle), PetContainer
{
    private lateinit var petListView: PetListView

    override fun onFinishInflate() {
        super.onFinishInflate()
        petListView = getChildAt(0) as PetListView
    }

    override fun render(uiState: PetListUiState) {
        if(uiState.selectedPet != null) {
            // Remove the list view if it's attached
            if(listViewAttached()) {
                removeView(petListView)
            }

            // Add the detail view if it isn't already attached
            if(childCount == 0)
            {
                val detailView = PetDetailView(context)
                addView(detailView)
            }

            // Render the detail view
            (getChildAt(0) as PetDetailView).render(uiState)

        }
        else {
            petListView.render(uiState)
        }
    }

    override fun onBackPressed(): Boolean {
        // Details are showing
        if(!listViewAttached()) {
            removeViewAt(0) // Remove the detail view
            addView(petListView)
            return true
        }

        return false
    }

    private fun listViewAttached(): Boolean {
        return petListView.parent != null
    }

}