package ru.capjack.lib.kt.reflect

import ru.capjack.lib.kt.reflect.internal.ref
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass

actual val KAnnotatedElement.annotationsRef: List<Annotation>
	get() = ref.annotations

actual fun <T : Annotation> KAnnotatedElement.findAnnotation(clazz: KClass<T>): T? {
	return ref.annotations.find { clazz.isInstance(it) }.unsafeCast<T?>()
}