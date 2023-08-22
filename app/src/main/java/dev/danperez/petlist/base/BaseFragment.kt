package dev.danperez.petlist.base

import android.content.Context
import androidx.fragment.app.Fragment
import dev.danperez.petlist.PetListApplication
import dev.danperez.scopes.AppScope
import dev.danperez.scopes.codegen.AnvilInjector
import dev.danperez.scopes.codegen.InjectWith

open class BaseFragment: Fragment()
{
    override fun onAttach(context: Context) {
        super.onAttach(context)
        anvilInject()
    }

    private fun anvilInject() {
//        val scope  (this::class.annotations.find { it is InjectWith } as? InjectWith)?.value
//        if(scope == AppScope::class) {
//            val injector: AnvilInjector<BaseFragment>? = (requireContext().applicationContext as PetListApplication).appComponent
//                .injectors()[javaClass] as? AnvilInjector<BaseFragment>
//
//            injector?.inject(this)
//        }
    }
}