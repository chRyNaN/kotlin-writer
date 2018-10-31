@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import kotlin.reflect.KClass

class KotlinInterfaceBuilder(private val builder: TypeSpec.Builder) {

    fun companionObject(name: String? = null, block: KotlinObjectBuilder.() -> Unit) {
        val companionObjectBuilder = KotlinObjectBuilder(TypeSpec.companionObjectBuilder(name))
        block(companionObjectBuilder)
        builder.addType(companionObjectBuilder.build())
    }

    fun superInterface(interfaceTypeName: TypeName, delegate: CodeBlock = CodeBlock.of("")) {
        builder.addSuperinterface(interfaceTypeName)
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

    fun property(name: String, type: Type, block: (KotlinPropertyBuilder.() -> Unit)? = null) {
        val propertyBuilder =
            KotlinPropertyBuilder(builder = PropertySpec.builder(name, type), interfaceProperty = true)
        block?.invoke(propertyBuilder)
        builder.addProperty(propertyBuilder.build())
    }

    fun property(name: String, type: TypeName, block: (KotlinPropertyBuilder.() -> Unit)? = null) {
        val propertyBuilder =
            KotlinPropertyBuilder(builder = PropertySpec.builder(name, type), interfaceProperty = true)
        block?.invoke(propertyBuilder)
        builder.addProperty(propertyBuilder.build())
    }

    fun property(name: String, type: KClass<*>, block: (KotlinPropertyBuilder.() -> Unit)? = null) {
        val propertyBuilder =
            KotlinPropertyBuilder(builder = PropertySpec.builder(name, type), interfaceProperty = true)
        block?.invoke(propertyBuilder)
        builder.addProperty(propertyBuilder.build())
    }

    fun function(name: String, block: (KotlinFunctionBuilder.() -> Unit)) {
        val functionBuilder = KotlinFunctionBuilder(builder = FunSpec.builder(name), interfaceFunction = true)
        block(functionBuilder)
        builder.addFunction(functionBuilder.build())
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

    fun nestedEnumClass(name: String, block: KotlinEnumBuilder.() -> Unit) {
        val nestedEnumClassBuilder = KotlinEnumBuilder(TypeSpec.enumBuilder(name))
        block(nestedEnumClassBuilder)
        builder.addType(nestedEnumClassBuilder.build())
    }

    fun nestedEnumClass(className: ClassName, block: KotlinEnumBuilder.() -> Unit) {
        val nestedEnumClassBuilder = KotlinEnumBuilder(TypeSpec.enumBuilder(className))
        block(nestedEnumClassBuilder)
        builder.addType(nestedEnumClassBuilder.build())
    }

    fun build() = builder.build()
}