@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import kotlin.reflect.KClass

class KotlinEnumBuilder internal constructor(private val builder: TypeSpec.Builder) {

    var primaryConstructor: KotlinConstructorBuilder.() -> Unit = {}
        set(value) {
            field = value

            val primaryConstructorBuilder = KotlinConstructorBuilder(FunSpec.constructorBuilder())
            value(primaryConstructorBuilder)
            builder.primaryConstructor(primaryConstructorBuilder.build())
        }

    fun enumConstant(name: String) {
        builder.addEnumConstant(name)
    }

    fun enumConstant(name: String, block: KotlinAnonymousClassBuilder.() -> Unit) {
        val anonymousClassBuilder = KotlinAnonymousClassBuilder(TypeSpec.anonymousClassBuilder())
        block(anonymousClassBuilder)
        builder.addEnumConstant(name, anonymousClassBuilder.build())
    }

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

    internal fun build() = builder.build()
}