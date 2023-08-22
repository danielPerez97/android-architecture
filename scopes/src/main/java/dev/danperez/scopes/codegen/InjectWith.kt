package dev.danperez.scopes.codegen

import kotlin.reflect.KClass

annotation class InjectWith(val value: KClass<*>)
