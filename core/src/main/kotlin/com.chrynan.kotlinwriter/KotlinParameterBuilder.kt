@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.*
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

class KotlinParameterBuilder internal constructor(private val builder: ParameterSpec.Builder) {

    var defaultValue: CodeBlock = CodeBlock.of("")
        set(value) {
            field = value

            builder.defaultValue(value)
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

    fun jvmModifiers(vararg modifiers: Modifier) {
        builder.jvmModifiers(modifiers.toList())
    }

    internal fun build() = builder.build()
}