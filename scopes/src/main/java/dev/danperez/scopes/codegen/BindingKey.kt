package dev.danperez.scopes.codegen

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class BindingKey( val clazz: KClass<*>)