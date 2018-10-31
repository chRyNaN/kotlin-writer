@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.CodeBlock

class KotlinCodeBlockBuilder(private val builder: CodeBlock.Builder) {

    fun named(format: String, args: Map<String, *>) = builder.addNamed(format, args)

    fun named(args: Map<String, *>, block: () -> String) = builder.addNamed(block(), args)

    fun code(format: String, vararg args: Any?) = builder.add(format, args)

    fun code(vararg args: Any?, block: () -> String) = builder.add(block(), args)

    fun beginControlFlow(controlFlow: String, vararg args: Any?) = builder.beginControlFlow(controlFlow, args)

    fun beginControlFlow(vararg args: Any?, block: () -> String) = builder.beginControlFlow(block(), args)

    fun nextControlFlow(controlFlow: String, vararg args: Any?) = builder.nextControlFlow(controlFlow, args)

    fun nextControlFlow(vararg args: Any?, block: () -> String) = builder.nextControlFlow(block(), args)

    fun endControlFlow() = builder.endControlFlow()

    fun statement(format: String, vararg args: Any?) = builder.addStatement(format, args)

    fun statement(vararg args: Any?, block: () -> String) = builder.addStatement(block(), args)

    fun codeBlock(codeBlock: CodeBlock) = builder.add(codeBlock)

    fun indent() = builder.indent()

    fun unindent() = builder.unindent()

    fun build() = builder.build()
}

fun codeBlock(block: KotlinCodeBlockBuilder.() -> Unit): CodeBlock {
    val codeBlockBuilder = KotlinCodeBlockBuilder(CodeBlock.builder())
    block(codeBlockBuilder)
    return codeBlockBuilder.build()
}

fun codeBlockString(block: () -> String): CodeBlock {
    val codeBlockBuilder = CodeBlock.builder()
    codeBlockBuilder.add(block())
    return codeBlockBuilder.build()
}

fun codeBlockWithArgs(vararg args: Any, block: () -> String): CodeBlock {
    val codeBlockBuilder = CodeBlock.builder()
    codeBlockBuilder.add(block(), args)
    return codeBlockBuilder.build()
}