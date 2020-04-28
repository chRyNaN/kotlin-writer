# kotlin-writer
Kotlin DSL library for generating Kotlin Files in an annotation processor

This library wraps the [KotlinPoet](https://github.com/square/kotlinpoet) library under the hood but provides a Kotlin DSL approach to generating Kotlin files.

[![](https://jitpack.io/v/chRyNaN/kotlin-writer.svg)](https://jitpack.io/#chRyNaN/kotlin-writer)

## Building

### Repository
```kotlin
repositories {
    maven {
        url = uri("https://dl.bintray.com/chrynan/chrynan")
    }
}
```

### Dependency
```kotlin
implementation("com.chrynan.kotlin-writer:kotlin-writer:VERSION")
```

## Status
This library is still new and possibly incomplete and error prone. Use with caution.

## Example
The [KotlinPoet](https://github.com/square/kotlinpoet) library uses the following example:
```kotlin
class Greeter(val name: String) {
  fun greet() {
    println("Hello, $name")
  }
}

fun main(vararg args: String) {
  Greeter(args[0]).greet()
}
```

**KotlinPoet:**
```kotlin
val greeterClass = ClassName("", "Greeter")
val file = FileSpec.builder("", "HelloWorld")
    .addType(TypeSpec.classBuilder("Greeter")
        .primaryConstructor(FunSpec.constructorBuilder()
            .addParameter("name", String::class)
            .build())
        .addProperty(PropertySpec.builder("name", String::class)
            .initializer("name")
            .build())
        .addFunction(FunSpec.builder("greet")
            .addStatement("println(%S)", "Hello, \$name")
            .build())
        .build())
    .addFunction(FunSpec.builder("main")
        .addParameter("args", String::class, VARARG)
        .addStatement("%T(args[0]).greet()", greeterClass)
        .build())
    .build()

file.writeTo(System.out)
```

**Kotlin-Writer (This Library):**
```kotlin
val file = kotlinFile("", "HelloWorld") {
        nestedClass("Greeter") {
            primaryConstructor = {
                parameter("name", String::class)
            }

            function("greet") {
                body = codeBlockWithArgs("Hello, \$name") { "println(%s)" }
            }
        }

        function("main") {
            parameter("args", String::class) { modifiers(KModifier.VARARG) }

            body = codeBlockString { "Greeter(args[0]).greet()" }
        }
    }
    
file.writeTo(System.out) // Or, to generate the file, use KotlinFileWriter: kotlinFileWriter.write(file)
```
