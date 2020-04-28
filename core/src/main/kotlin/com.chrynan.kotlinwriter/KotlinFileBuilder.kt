@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import kotlin.reflect.KClass

class KotlinFileBuilder internal constructor(private val spec: FileSpec.Builder) {

    fun import(constant: Enum<*>) {
        spec.addImport(constant = constant)
    }

    fun import(packageName: String, vararg names: String) {
        spec.addImport(packageName, *names)
    }

    fun import(clazz: Class<*>, vararg names: String) {
        spec.addImport(clazz, *names)
    }

    fun import(clazz: KClass<*>, vararg names: String) {
        spec.addImport(clazz, *names)
    }

    fun import(className: ClassName, vararg names: String) {
        spec.addImport(className, *names)
    }

    fun aliasedImport(className: ClassName, alias: String) {
        spec.addAliasedImport(className, alias)
    }

    fun aliasedImport(className: ClassName, memberName: String, alias: String) {
        spec.addAliasedImport(className, memberName, alias)
    }

    fun aliasedImport(clazz: KClass<*>, alias: String) {
        spec.addAliasedImport(clazz, alias)
    }

    fun aliasedImport(clazz: Class<*>, alias: String) {
        spec.addAliasedImport(clazz, alias)
    }

    fun typeAlias(name: String, type: Type, block: KotlinTypeAliasBuilder.() -> Unit) {
        val builder = KotlinTypeAliasBuilder(TypeAliasSpec.builder(name = name, type = type))
        block(builder)
        spec.addTypeAlias(builder.build())
    }

    fun typeAlias(name: String, type: TypeName, block: KotlinTypeAliasBuilder.() -> Unit) {
        val builder = KotlinTypeAliasBuilder(TypeAliasSpec.builder(name = name, type = type))
        block(builder)
        spec.addTypeAlias(builder.build())
    }

    fun typeAlias(name: String, type: KClass<*>, block: KotlinTypeAliasBuilder.() -> Unit) {
        val builder = KotlinTypeAliasBuilder(TypeAliasSpec.builder(name = name, type = type))
        block(builder)
        spec.addTypeAlias(builder.build())
    }

    fun annotation(clazz: KClass<out Annotation>, block: (KotlinAnnotationBuilder.() -> Unit)? = null) {
        val builder = KotlinAnnotationBuilder(AnnotationSpec.builder(clazz))
        block?.invoke(builder)
        spec.addAnnotation(builder.build())
    }

    fun annotation(clazz: Class<out Annotation>, block: (KotlinAnnotationBuilder.() -> Unit)? = null) {
        val builder = KotlinAnnotationBuilder(AnnotationSpec.builder(clazz))
        block?.invoke(builder)
        spec.addAnnotation(builder.build())
    }

    fun annotation(className: ClassName, block: (KotlinAnnotationBuilder.() -> Unit)? = null) {
        val builder = KotlinAnnotationBuilder(AnnotationSpec.builder(className))
        block?.invoke(builder)
        spec.addAnnotation(builder.build())
    }

    fun comment(format: String, vararg args: Any) {
        spec.addComment(format, args)
    }

    fun property(name: String, type: Type, block: KotlinPropertyBuilder.() -> Unit) {
        val builder = KotlinPropertyBuilder(PropertySpec.builder(name, type))
        block(builder)
        spec.addProperty(builder.build())
    }

    fun property(name: String, type: TypeName, block: KotlinPropertyBuilder.() -> Unit) {
        val builder = KotlinPropertyBuilder(PropertySpec.builder(name, type))
        block(builder)
        spec.addProperty(builder.build())
    }

    fun property(name: String, type: KClass<*>, block: KotlinPropertyBuilder.() -> Unit) {
        val builder = KotlinPropertyBuilder(PropertySpec.builder(name, type))
        block(builder)
        spec.addProperty(builder.build())
    }

    fun function(name: String, block: (KotlinFunctionBuilder.() -> Unit)) {
        val builder = KotlinFunctionBuilder(FunSpec.builder(name))
        block(builder)
        spec.addFunction(builder.build())
    }

    fun nestedClass(name: String, block: KotlinClassBuilder.() -> Unit) {
        val nestedClassBuilder = KotlinClassBuilder(TypeSpec.classBuilder(name))
        block(nestedClassBuilder)
        spec.addType(nestedClassBuilder.build())
    }

    fun nestedClass(className: ClassName, block: KotlinClassBuilder.() -> Unit) {
        val nestedClassBuilder = KotlinClassBuilder(TypeSpec.classBuilder(className))
        block(nestedClassBuilder)
        spec.addType(nestedClassBuilder.build())
    }

    fun nestedInterface(name: String, block: KotlinInterfaceBuilder.() -> Unit) {
        val nestedInterfaceBuilder = KotlinInterfaceBuilder(TypeSpec.interfaceBuilder(name))
        block(nestedInterfaceBuilder)
        spec.addType(nestedInterfaceBuilder.build())
    }

    fun nestedInterface(className: ClassName, block: KotlinInterfaceBuilder.() -> Unit) {
        val nestedInterfaceBuilder = KotlinInterfaceBuilder(TypeSpec.interfaceBuilder(className))
        block(nestedInterfaceBuilder)
        spec.addType(nestedInterfaceBuilder.build())
    }

    fun nestedObject(name: String, block: KotlinObjectBuilder.() -> Unit) {
        val nestedObjectBuilder = KotlinObjectBuilder(TypeSpec.objectBuilder(name))
        block(nestedObjectBuilder)
        spec.addType(nestedObjectBuilder.build())
    }

    fun nestedObject(className: ClassName, block: KotlinObjectBuilder.() -> Unit) {
        val nestedObjectBuilder = KotlinObjectBuilder(TypeSpec.objectBuilder(className))
        block(nestedObjectBuilder)
        spec.addType(nestedObjectBuilder.build())
    }

    fun nestedEnumClass(name: String, block: KotlinEnumBuilder.() -> Unit) {
        val nestedEnumBuilder = KotlinEnumBuilder(TypeSpec.enumBuilder(name))
        block(nestedEnumBuilder)
        spec.addType(nestedEnumBuilder.build())
    }

    fun nestedEnumClass(className: ClassName, block: KotlinEnumBuilder.() -> Unit) {
        val nestedEnumBuilder = KotlinEnumBuilder(TypeSpec.enumBuilder(className))
        block(nestedEnumBuilder)
        spec.addType(nestedEnumBuilder.build())
    }

    internal fun build() = spec.build()
}

fun kotlinFile(packageName: String, fileName: String, block: KotlinFileBuilder.() -> Unit): FileSpec {
    val fileBuilder = KotlinFileBuilder(
        FileSpec.builder(
            packageName = packageName,
            fileName = fileName
        )
    )
    block(fileBuilder)
    return fileBuilder.build()
}