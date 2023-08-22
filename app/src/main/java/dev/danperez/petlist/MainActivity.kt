package dev.danperez.petlist

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import timber.log.Timber
import javax.inject.Inject

class MainActivity : FragmentActivity() {

    @Inject lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as PetListApplication).appComponent.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate()")
    }
}