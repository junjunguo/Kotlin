# Kotlin
Learn Kotlin Programming Language

## Kotlin Programming Language

## Resources

### Kotlin programming language docs
#### [Kotlin basic](https://kotlinlang.org/docs/reference/basic-types.html)
#### [Class and Inheritance](https://kotlinlang.org/docs/reference/classes.html)
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



