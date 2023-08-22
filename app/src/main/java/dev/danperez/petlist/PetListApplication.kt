package dev.danperez.petlist

import android.app.Application
import dev.danperez.petlist.di.AppComponent
import dev.danperez.petlist.di.DaggerAppComponent
import timber.log.Timber

class PetListApplication: Application()
{
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}