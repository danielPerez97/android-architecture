package dev.danperez.petlist.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dev.danperez.petdb.PetDb
import dev.danperez.scopes.AppScope
import dev.danperez.scopes.SingleIn

@Module
@ContributesTo(AppScope::class)
class PetDbModule {

    @Provides
    @SingleIn(AppScope::class)
    fun providePetDb(context: Context): PetDb
    {
        val driver: SqlDriver = AndroidSqliteDriver(PetDb.Schema, context, "pets.db")
        return PetDb(driver)
    }
}