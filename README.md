# Kotlin
Learn & build Kotlin + spring based backend


Kotlin Repository:
1. basic: Kotlin basic

2. [springBoot](springBoot/): spring boot + gradle + RESTful api

3. [springsql](springsql/): spring boot + gradle + PostgreSQL + RESTful api

4. [springrestful](springrestful/): spring ([boot](https://github.com/spring-projects/spring-boot) + [security](https://github.com/spring-projects/spring-security)) + [gradle](https://gradle.org/) + [OAuth2](https://oauth.net/2/) + [JWT](https://jwt.io/) + [PostgreSQL](https://www.postgresql.org/) + [RESTful api](https://restfulapi.net/)

5. [fullstack](fullstack/): in progress ...

	5.1 [backend](fullstack/backend/): in progress ...

	5.2 [frontend](fullstack/frontend/): in progress ...


## Kotlin Programming Language

## Resources

### Kotlin programming language docs
#### [Keywords and Operators](https://kotlinlang.org/docs/reference/keyword-reference.html)
#### [Using Kotlin for Server-side Development](https://kotlinlang.org/docs/reference/server-overview.html)
#### [Kotlin basic](https://kotlinlang.org/docs/reference/basic-types.html)
#### [Class and Inheritance](https://kotlinlang.org/docs/reference/classes.html)
##### [Visibility Modifiers](https://kotlinlang.org/docs/reference/visibility-modifiers.html)
> Classes, objects, interfaces, constructors, functions, properties and their setters can have visibility modifiers. (Getters always have the same visibility as the property.) 

There are four visibility modifiers in Kotlin: 
- private: `only be visible inside the file containing the declaration`
- protected: `is not available for top-level declarations`
- internal: `is visible everywhere in the same module`
- public. 

The default visibility, used if there is no explicit modifier, is public.

Packages:

```kotlin
// file name: example.kt
package foo

private fun foo() {} // visible inside example.kt

public var bar: Int = 5 // property is visible everywhere
    private set         // setter is visible only in example.kt
    
internal val baz = 6    // visible inside the same module
```

**Classes and Interfaces:**
For members declared inside a class:

- `private` means visible inside this class only (including all its members);
- `protected` — same as private + visible in subclasses too;
- `internal` — any client inside this module who sees the declaring class sees its internal members;
- `public` — any client who sees the declaring class sees its public members.

```kotlin
open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4  // public by default
    
    protected class Nested {
        public val e: Int = 5
    }
}

class Subclass : Outer() {
    // a is not visible
    // b, c and d are visible
    // Nested and e are visible

    override val b = 5   // 'b' is protected
}

class Unrelated(o: Outer) {
    // o.a, o.b are not visible
    // o.c and o.d are visible (same module)
    // Outer.Nested is not visible, and Nested::e is not visible either 
}
```



#### [Functions and Lambdas](https://kotlinlang.org/docs/reference/functions.html)
#### [Comparison to Java Programming Language](https://kotlinlang.org/docs/reference/comparison-to-java.html)
#### Others
- [Collections: List, Set, Map](https://kotlinlang.org/docs/reference/collections.html)
- [Null Safety](https://kotlinlang.org/docs/reference/null-safety.html)
- [This Expression](https://kotlinlang.org/docs/reference/this-expressions.html)
- [Annotations](https://kotlinlang.org/docs/reference/annotations.html)
- [Calling Java code from Kotlin](https://kotlinlang.org/docs/reference/java-interop.html)
- [Calling Kotlin from Java](https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html)
- [Type Aliases](https://kotlinlang.org/docs/reference/type-aliases.html)

### Spring
- [Introducing Kotlin support in Spring Framework 5.0](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0)
- [Developing Spring Boot applications with Kotlin](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin)

### Articles and Blogs

#### [Kotlin programmer dictionary](https://blog.kotlin-academy.com/kotlin-programmer-dictionary-2cb67fff1fe2)
- [Kotlin programmer dictionary: Function vs Method vs Procedure](https://blog.kotlin-academy.com/kotlin-programmer-dictionary-function-vs-method-vs-procedure-c0216642ee87)
- [Kotlin programmer dictionary: Statement vs Expression](https://blog.kotlin-academy.com/kotlin-programmer-dictionary-statement-vs-expression-e6743ba1aaa0)
- [Kotlin Programmer Dictionary: Function Type vs Function literal vs Lambda expression vs Anonymous function](https://blog.kotlin-academy.com/kotlin-programmer-dictionary-function-type-vs-function-literal-vs-lambda-expression-vs-anonymous-edc97e8873e)

#### Kotlin Tutorial 12 
- [Kotlin Tutorial 12 — Encapsulation And Polymorphism](https://kotlinlang.org/docs/reference/visibility-modifiers.html)

#### Kotlin performances
- [Kotlin's hidden costs - Benchmarks](https://sites.google.com/a/athaydes.com/renato-athaydes/posts/kotlinshiddencosts-benchmarks)

## [Android Kotlin Guides](https://android.github.io/kotlin-guides/style.html)
## [Coding Conventions](https://kotlinlang.org/docs/reference/coding-conventions.html)

### Source file names
- its name should be the same as the name of the class
	- `ProcessDeclarations.kt`
- The name of the file should describe what the code in the file does

### Naming rules
- Names of packages are always lower case and do not use underscores 	
	- `org.example.myproject`
- Names of classes and objects start with an upper case letter and use camel humps:
	- `open class DeclarationProcessor { ... }`
	- `object EmptyDeclarationProcessor : DeclarationProcessor() { ... }`
- Function names
	- Names of `functions`, `properties` and local `variables` **start with a lower case letter** and use camel humps and no underscores
	- `fun processDeclarations() { ... }`
- Property names
	- Names of constants (properties marked with const, or top-level or object val properties with no custom get function that hold deeply immutable data) should use uppercase underscore-separated names
		- `const val MAX_COUNT = 8`
		- `val USER_NAME_FIELD = "UserName"`
	- Names of properties holding references to singleton objects can use the same naming style as object declarations:
		- `val PersonComparator: Comparator<Person> = ...`

### Formatting
#### Modifiers 
If a declaration has multiple modifiers, always put them in the following order:

```kotlin
public / protected / private / internal
expect / actual
final / open / abstract / sealed / const
external
override
lateinit
tailrec
vararg
suspend
inner
enum / annotation
companion
inline
infix
operator
data
```

Place all annotations before modifiers:

```kotlin
@Named("Foo")
private val foo: Foo
```

### Idiomatic use of language features
#### Immutability
- Prefer using immutable data to mutable. Always declare local variables and properties as `val` rather than `var` if they are not modified after initialization.
- Always use immutable collection interfaces (Collection, List, Set, Map) to declare collections which are not mutated. When using factory functions to create collection instances, always use functions that return immutable collection types when possible:

```kotlin
// Bad: use of mutable collection type for value which will not be mutated
fun validateValue(actualValue: String, allowedValues: HashSet<String>) { ... }

// Good: immutable collection type used instead
fun validateValue(actualValue: String, allowedValues: Set<String>) { ... }

// Bad: arrayListOf() returns ArrayList<T>, which is a mutable collection type
val allowedValues = arrayListOf("a", "b", "c")

// Good: listOf() returns List<T>
val allowedValues = listOf("a", "b", "c")
```

#### Default parameter values
Prefer declaring functions with default parameter values to declaring overloaded functions.

```kotlin
// Bad
fun foo() = foo("a")
fun foo(a: String) { ... }

// Good
fun foo(a: String = "a") { ... }

```

#### Type aliases 
If you have a functional type or a type with type parameters which is used multiple times in a codebase, prefer defining a type alias for it:

```kotlin
typealias MouseClickHandler = (Any, MouseEvent) -> Unit
typealias PersonIndex = Map<String, Person>
```

## Gradle
> Gradle is a build tool with a focus on build automation and support for multi-language development. If you are building, testing, publishing, and deploying software on any platform, Gradle offers a flexible model that can support the entire development lifecycle from compiling and packaging code to publishing web sites. Gradle has been designed to support build automation across multiple languages and platforms including Java, Scala, Android, C/C++, and Groovy, and is closely integrated with development tools and continuous integration servers including Eclipse, IntelliJ, and Jenkins.

#### [Kotlin meets Gradle](https://blog.gradle.org/kotlin-meets-gradle)
#### [Gradle Kotlin DSL](https://github.com/gradle/kotlin-dsl)
> The Gradle Kotlin DSL provides support for writing Gradle build scripts using JetBrains' Kotlin language. It aims to provide Gradle users with a rich, flexible and statically-typed approach to developing build logic in conjunction with the best IDE and tooling experience possible.

## Spring

### Spring Boot with Kotlin
#### [Spring blog: Developing Spring Boot applications with Kotlin](https://spring.io/blog/2016/02/15/developing-spring-boot-applications-with-kotlin)
- [demo](https://github.com/sdeleuze/spring-boot-kotlin-demo)

#### [Introducing Kotlin support in Spring Framework 5.0](https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0)

