@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

class KotlinConstructorBuilder internal constructor(private val builder: FunSpec.Builder) {

    var body: CodeBlock = CodeBlock.of("")
        set(value) {
            field = value

            builder.addCode(value)
        }

    fun callThisConstructor(vararg args: CodeBlock = emptyArray()) {
        builder.callThisConstructor(*args)
    }

    fun callSuperConstructor(vararg args: CodeBlock = emptyArray()) {
        builder.callSuperConstructor(*args)
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

    fun typeVariables(vararg typeVariables: TypeVariableName) {
        builder.addTypeVariables(typeVariables.toList())
    }

    fun parameter(name: String, type: TypeName, block: (KotlinParameterBuilder.() -> Unit)? = null) {
        val parameterBuilder = KotlinParameterBuilder(ParameterSpec.builder(name, type))
        block?.invoke(parameterBuilder)
        builder.addParameter(parameterBuilder.build())
    }

    fun parameter(name: String, type: Type, block: (KotlinParameterBuilder.() -> Unit)? = null) {
        val parameterBuilder = KotlinParameterBuilder(ParameterSpec.builder(name, type))
        block?.invoke(parameterBuilder)
        builder.addParameter(parameterBuilder.build())
    }

    fun parameter(name: String, type: KClass<*>, block: (KotlinParameterBuilder.() -> Unit)? = null) {
        val parameterBuilder = KotlinParameterBuilder(ParameterSpec.builder(name, type))
        block?.invoke(parameterBuilder)
        builder.addParameter(parameterBuilder.build())
    }

    internal fun build() = builder.build()
}