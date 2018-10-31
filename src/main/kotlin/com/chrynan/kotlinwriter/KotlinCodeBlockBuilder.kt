@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.CodeBlock

class KotlinCodeBlockBuilder(private val builder: CodeBlock.Builder) {

    // TODO add DSL functions for CodeBlock.Builder

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