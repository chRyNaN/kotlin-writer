@file:Suppress("unused")

package com.chrynan.kotlinwriter

import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.ProcessingEnvironment

class KotlinFileWriter(private val processingEnvironment: ProcessingEnvironment) {

    companion object {

        const val KOTLIN_GENERATED_FILES_BASE_LOCATION = "kapt.kotlin.generated"
    }

    fun write(fileSpec: FileSpec) = fileSpec.writeTo(getKotlinBaseGeneratedFile())

    private fun getKotlinBaseGeneratedFile() =
        File(processingEnvironment.options[KOTLIN_GENERATED_FILES_BASE_LOCATION] ?: "")
}

fun FileSpec.writeWith(writer: KotlinFileWriter) = writer.write(this)