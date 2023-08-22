package dev.danperez.petlist.screens.petlist

 import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.squareup.anvil.annotations.ContributesMultibinding
import dev.danperez.petlist.R
import dev.danperez.petlist.base.BaseFragment
import dev.danperez.petlist.di.FragmentKey
import dev.danperez.petlist.screens.petlist.container.PetContainer
import dev.danperez.scopes.AppScope
import dev.marcellogalhardo.retained.fragment.retain
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@ContributesMultibinding(AppScope::class, Fragment::class)
@FragmentKey(PetListFragment::class)
class PetListFragment @Inject constructor(
    private val petListViewModelFactory: Provider<PetListViewModel.Factory>
): BaseFragment()
{
    private val viewModel: PetListViewModel by retain { entry ->
        petListViewModelFactory.get().create(entry.scope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState().collect {
                (view as PetContainer).render(it)
            }
        }
    }
}
