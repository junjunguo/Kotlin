package kotlin.junjunguo.com.classes

class InitOrderDemo(name: String) {
    val firstProperty = "First : $name".also(::println)

    init {
        println("First initializer block prints ${name}")
    }

    val secondProperty = "Second: ${name.length}".also(::println)

    init {
        println("Second initializer prints ${name.length}")
    }
}