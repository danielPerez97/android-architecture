package dev.danperez.petlist.di

import android.content.Context
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import dev.danperez.petlist.MainActivity
import dev.danperez.petlist.screens.petlist.PetListFragment
import dev.danperez.scopes.AppScope
import dev.danperez.scopes.SingleIn
import dev.danperez.scopes.codegen.AnvilInjector

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent
{
    fun inject(activity: MainActivity)

    fun inject(fragment: PetListFragment)

//    fun injectors(): Map<Class<*>, AnvilInjector<*>>

    @Component.Factory
    interface Factory
    {
        fun create(@BindsInstance context: Context): AppComponent
    }
}