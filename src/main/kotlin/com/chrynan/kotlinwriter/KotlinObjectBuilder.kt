@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import kotlin.reflect.KClass

class KotlinObjectBuilder internal constructor(private val builder: TypeSpec.Builder) {

    var superClass: TypeName? = null
        set(value) {
            field = value

            value?.let { builder.superclass(it) } ?: builder.superclass(Any::class)
        }
    var initBlock: CodeBlock = CodeBlock.of("")
        set(value) {
            field = value

            builder.addInitializerBlock(value)
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

    fun superInterface(interfaceTypeName: TypeName, delegate: CodeBlock = CodeBlock.of("")) {
        builder.addSuperinterface(interfaceTypeName)
    }

    fun property(name: String, type: Type, block: KotlinPropertyBuilder.() -> Unit) {
        val propertyBuilder = KotlinPropertyBuilder(PropertySpec.builder(name, type))
        block(propertyBuilder)
        builder.addProperty(propertyBuilder.build())
    }

    fun property(name: String, type: TypeName, block: KotlinPropertyBuilder.() -> Unit) {
        val propertyBuilder = KotlinPropertyBuilder(PropertySpec.builder(name, type))
        block(propertyBuilder)
        builder.addProperty(propertyBuilder.build())
    }

    fun property(name: String, type: KClass<*>, block: KotlinPropertyBuilder.() -> Unit) {
        val propertyBuilder = KotlinPropertyBuilder(PropertySpec.builder(name, type))
        block(propertyBuilder)
        builder.addProperty(propertyBuilder.build())
    }

    fun function(name: String, block: (KotlinFunctionBuilder.() -> Unit)) {
        val functionBuilder = KotlinFunctionBuilder(FunSpec.builder(name))
        block(functionBuilder)
        builder.addFunction(functionBuilder.build())
    }

    fun nestedClass(name: String, block: KotlinClassBuilder.() -> Unit) {
        val nestedClassBuilder = KotlinClassBuilder(TypeSpec.classBuilder(name))
        block(nestedClassBuilder)
        builder.addType(nestedClassBuilder.build())
    }

    fun nestedClass(className: ClassName, block: KotlinClassBuilder.() -> Unit) {
        val nestedClassBuilder = KotlinClassBuilder(TypeSpec.classBuilder(className))
        block(nestedClassBuilder)
        builder.addType(nestedClassBuilder.build())
    }

    fun nestedObject(name: String, block: KotlinObjectBuilder.() -> Unit) {
        val nestedObjectBuilder = KotlinObjectBuilder(TypeSpec.objectBuilder(name))
        block(nestedObjectBuilder)
        builder.addType(nestedObjectBuilder.build())
    }

    fun nestedObject(className: ClassName, block: KotlinObjectBuilder.() -> Unit) {
        val nestedObjectBuilder = KotlinObjectBuilder(TypeSpec.objectBuilder(className))
        block(nestedObjectBuilder)
        builder.addType(nestedObjectBuilder.build())
    }

    internal fun build() = builder.build()
}