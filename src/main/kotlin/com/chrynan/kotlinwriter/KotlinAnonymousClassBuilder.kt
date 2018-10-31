package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.TypeSpec

class KotlinAnonymousClassBuilder(private val builder: TypeSpec.Builder) {

    fun build() = builder.build()
}