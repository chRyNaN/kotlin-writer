@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock

class KotlinAnnotationBuilder(private val builder: AnnotationSpec.Builder) {

    var useSiteTarget: AnnotationSpec.UseSiteTarget? = null
        set(value) {
            field = value

            builder.useSiteTarget(value)
        }

    fun member(format: String, vararg args: Any) {
        builder.addMember(format, args)
    }

    fun member(block: CodeBlock) {
        builder.addMember(block)
    }

    fun build() = builder.build()
}