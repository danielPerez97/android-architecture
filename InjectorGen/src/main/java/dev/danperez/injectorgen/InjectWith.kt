package dev.danperez.injectorgen

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
annotation class InjectWith( val value: KClass<*> )
