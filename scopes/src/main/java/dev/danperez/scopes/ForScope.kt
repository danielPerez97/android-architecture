package dev.danperez.scopes

import javax.inject.Qualifier
import kotlin.reflect.KClass

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ForScope(val value: KClass<*>)
