package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.TypeSpec

class KotlinAnonymousClassBuilder internal constructor(private val builder: TypeSpec.Builder) {

    internal fun build() = builder.build()
}