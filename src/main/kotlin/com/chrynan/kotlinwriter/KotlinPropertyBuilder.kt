@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

class KotlinPropertyBuilder internal constructor(
    private val builder: PropertySpec.Builder,
    interfaceProperty: Boolean = false
) {

    init {
        if (interfaceProperty) builder.addModifiers(KModifier.ABSTRACT)
    }

    var isMutable = false
        set(value) {
            field = value

            builder.mutable(value)
        }
    var receiver: TypeName? = null
        set(value) {
            field = value

            value?.let { builder.receiver(it) }
        }
    var initializer: CodeBlock? = null
        set(value) {
            field = value

            value?.let { builder.initializer(it) }
        }
    var delegate: CodeBlock? = null
        set(value) {
            field = value

            value?.let { builder.delegate(value) }
        }
    var getter: KotlinFunctionBuilder.() -> Unit = {}
        set(value) {
            field = value

            val functionBuilder = KotlinFunctionBuilder(FunSpec.getterBuilder())
            value(functionBuilder)
            builder.getter(functionBuilder.build())
        }
    var setter: KotlinFunctionBuilder.() -> Unit = {}
        set(value) {
            field = value

            val functionBuilder = KotlinFunctionBuilder(FunSpec.setterBuilder())
            value(functionBuilder)
            builder.getter(functionBuilder.build())
        }

    fun kDoc(format: String, args: Any) {
        builder.addKdoc(format, args)
    }

    fun kDoc(block: CodeBlock) {
        builder.addKdoc(block)
    }

    fun kDoc(block: CodeBlock.Builder.() -> Unit) {
        val codeBlockBuilder = CodeBlock.builder()
        block(codeBlockBuilder)
        builder.addKdoc(codeBlockBuilder.build())
    }

    fun annotation(clazz: KClass<out Annotation>, block: (KotlinAnnotationBuilder.() -> Unit)? = null) {
        val annotationBuilder = KotlinAnnotationBuilder(AnnotationSpec.builder(clazz))
        block?.invoke(annotationBuilder)
        builder.addAnnotation(annotationBuilder.build())
    }

    fun annotation(clazz: Class<out Annotation>, block: (KotlinAnnotationBuilder.() -> Unit)? = null) {
        val annotationBuilder = KotlinAnnotationBuilder(AnnotationSpec.builder(clazz))
        block?.invoke(annotationBuilder)
        builder.addAnnotation(annotationBuilder.build())
    }

    fun annotation(className: ClassName, block: (KotlinAnnotationBuilder.() -> Unit)? = null) {
        val annotationBuilder = KotlinAnnotationBuilder(AnnotationSpec.builder(className))
        block?.invoke(annotationBuilder)
        builder.addAnnotation(annotationBuilder.build())
    }

    fun modifiers(vararg modifiers: KModifier) {
        builder.addModifiers(*modifiers)
    }

    fun typeVariables(vararg typeVariables: TypeVariableName) {
        builder.addTypeVariables(typeVariables.toList())
    }

    internal fun build() = builder.build()
}